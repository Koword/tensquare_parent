package com.tensquare.notice.config;

import com.tensquare.notice.listener.SysNoticeListener;
import com.tensquare.notice.listener.UserNoticeListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {



    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 系统通知监听
     * @Date:  2020-1-30 0030 23:01
     * @Param  * @param connectionFactory
     * @Return: org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
     */
    @Bean("sysNoticeContainer")
    public SimpleMessageListenerContainer createSys(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);
        //使用Channel
        container.setExposeListenerChannel(true);
        //设置自己编写的监听器
        container.setMessageListener(new SysNoticeListener());
        return container;
    }



    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 用户点赞通知监听
     * @Date:  2020-1-30 0030 23:04
     * @Param  * @param connectionFactory
     * @Return: org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
     */
    @Bean("userNoticeContainer")
    public SimpleMessageListenerContainer createUser(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);
        //使用Channel
        container.setExposeListenerChannel(true);
        //设置自己编写的监听器
        container.setMessageListener(new UserNoticeListener());
        return container;
    }

}
