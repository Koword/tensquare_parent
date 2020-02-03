package com.tensquare.notice.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.notice.config.ApplicationContextProvider;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具体业务逻辑处理类
 * 先建立WebSocket连接
 * 首次登录：用户主动获取消息
 * 登录后再获取消息，都是服务端主动推送的 -> SysNoticeListener
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ObjectMapper MAPPER = new ObjectMapper();

    // 送Spring容器中获取消息监听器容器,处理订阅消息sysNotice
    SimpleMessageListenerContainer sysNoticeContainer = (SimpleMessageListenerContainer) ApplicationContextProvider.getApplicationContext()
            .getBean("sysNoticeContainer");

    SimpleMessageListenerContainer userNoticeContainer = (SimpleMessageListenerContainer) ApplicationContextProvider.getApplicationContext()
            .getBean("userNoticeContainer");
    //从Spring容器中获取RabbitTemplate
    RabbitTemplate rabbitTemplate = ApplicationContextProvider.getApplicationContext()
            .getBean(RabbitTemplate.class);

    //存放WebSocket连接Map，根据用户id存放
    public static ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap();

    //用户请求WebSocket服务端，执行的方法
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //约定用户第一次请求携带的数据：{"userId":"1"}
        //1.获取用户请求数据并解析
        String json = msg.text();
        //2.解析json数据，获取用户id
        String userId = MAPPER.readTree(json).get("userId").asText();


        //3.第一次请求的时候，需要建立WebSocket连接
        Channel channel = userChannelMap.get(userId);
        //4.如果是, 建立连接, 把连接存到Map
        if (channel == null || !channel.isActive()) {
            //获取WebSocket的连接
            channel = ctx.channel();
            //把连接放到容器中
            userChannelMap.put(userId, channel);
        }
        //5.获得当前用户队列的消息(数量)
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        //拼接获取队列名称
        String queueName = "article_subscribe_" + userId;
        String userQueueName = "article_thumbup_" + userId;
        //获取Rabbit的Properties容器
        Properties queueProperties = rabbitAdmin.getQueueProperties(queueName);
        Properties userQueueProperties = rabbitAdmin.getQueueProperties(userQueueName);
        //获取消息数量
        int noticeCount = 0;
        int userNoticeCount = 0;
        //判断Properties是否不为空
        if (queueProperties != null) {
            // 如果不为空，获取消息的数量
            noticeCount = (int) queueProperties.get("QUEUE_MESSAGE_COUNT");
        }
        if (userQueueProperties != null){
            // 如果不为空，获取消息的数量
            userNoticeCount = (int) userQueueProperties.get("QUEUE_MESSAGE_COUNT");
        }
        //6.把数据发送给用户
        //封装返回的数据
        HashMap countMap = new HashMap();
        //订阅类消息数量
        countMap.put("sysNoticeCount", noticeCount);
        countMap.put("userNoticeCount",userNoticeCount);
        Result result = new Result(true, StatusCode.OK, "查询成功", countMap);
        //把数据发送给用户
        channel.writeAndFlush(new TextWebSocketFrame(MAPPER.writeValueAsString(result)));

        //7.消费消息
        // 系统消息
        if (noticeCount > 0) {
            rabbitAdmin.purgeQueue(queueName, true);
        }
        // 用户点赞消息
        if (userNoticeCount > 0) {
            rabbitAdmin.purgeQueue(userQueueName, true);
        }
        // 首次登录 主动获取消息队列中的消息----end

        //8.设置队列的监听
        //为用户的消息通知队列注册监听器，便于用户在线的时候，
        //一旦有消息，可以主动推送给用户，不需要用户请求服务器获取数据
        sysNoticeContainer.addQueueNames(queueName);
        userNoticeContainer.addQueueNames(userQueueName);
     }
}
