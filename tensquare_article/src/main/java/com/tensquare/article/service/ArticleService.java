package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.client.NoticeClient;
import com.tensquare.article.config.RabbitmqConfig;
import com.tensquare.article.dao.ArticleMapper;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.pojo.Notice;
import com.tensquare.util.IdWorker;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-24 23:57:36
 **/
@Transactional(rollbackFor = Exception.class)
@Service
@AllArgsConstructor
public class ArticleService {

    ArticleMapper articleMapper;
    IdWorker idWorker;
    RedisTemplate redisTemplate;
    NoticeClient noticeClient;
    //    NoticeFreshClient noticeFreshClient;
    RabbitTemplate rabbitTemplate;
    DirectExchange directExchange;
    RabbitAdmin rabbitAdmin;

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 查询全部
     * @Date: 2019-11-25 0025 0:00
     * @Param * @param
     * @Return: java.util.List<com.tensquare.article.pojo.Article>
     */
    public List<Article> selectAll() {
        return articleMapper.selectList(null);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据id查询文章
     * @Date: 2019-11-25 0025 0:40
     * @Param * @param articleId
     * @Return: com.tensquare.article.pojo.Article
     */
    public Article selectById(String articleId) {
        return articleMapper.selectById(articleId);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 新增文章
     * @Date: 2019-11-25 0025 23:26
     * @Param * @param article
     * @Return: void
     */
    public void insert(Article article) {

        // 1.新增文章
        String authorId = "1";

        article.setId(idWorker.nextId() + "");
        article.setCreatetime(new Date());
        article.setIspublic("1");
        article.setVisits(0);           // 浏览量
        article.setThumbup(0);          // 点赞数
        article.setComment(0);          // 评论数
        article.setState("0");

        // 通知
        Set<String> members = redisTemplate.opsForSet().members("article_author_" + authorId);

        for (String uid : members) {
            // 消息通知
            Notice notice = new Notice();
            notice.setReceiverId(uid);
            notice.setOperatorId(authorId);
            notice.setAction("publish");
            notice.setTargetType("article");
            notice.setTargetId(article.getId());
            notice.setCreatetime(new Date());
            notice.setType("sys");
            notice.setState("0");
            noticeClient.add(notice);
        }

        articleMapper.insert(article);

        // 入库成功后，发送mq消息，内容是消息通知id
        // arg0：交换机名称   arg1：routingKey     arg2：随意
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ARTICLE, authorId, article.getId());

    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 修改文章
     * @Date: 2019-11-25 0025 0:40
     * @Param * @param article
     * @Return: void
     */
    public void updateById(Article article) {
        //方式一
        articleMapper.updateById(article);
        //方式二
//        EntityWrapper<Article> wrapper = new EntityWrapper<>();
//        wrapper.eq("id", article.getId());
//        articleMapper.update(article, wrapper);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 删除文章
     * @Date: 2019-11-25 0025 0:40
     * @Param * @param articleId
     * @Return: void
     */
    public void deleteById(String articleId) {
        articleMapper.deleteById(articleId);
    }


    /**
     * @Description 分页查询
     * @Author tangkai
     * @Date 14:38 2019/12/19
     * @Param [map, page, size]
     * @Return com.tensquare.entity.Result
     **/
    public Page<Article> search(Map map, int page, int size) {
        // 1.封装分页条件
        Page<Article> pageList = new Page<>(page, size);

        // 2.封装查询条件
        EntityWrapper<Article> entityWrapper = new EntityWrapper<>();
        if (!StringUtils.isEmpty(map.get("columnid"))) {
            entityWrapper.eq("columnid", map.get("columnid"));
        }
        if (!StringUtils.isEmpty(map.get("userid"))) {
            entityWrapper.eq("userid", map.get("userid"));
        }
        if (!StringUtils.isEmpty(map.get("title"))) {
            entityWrapper.like("title", "%" + map.get("title") + "%");
        }
        if (!StringUtils.isEmpty(map.get("content"))) {
            entityWrapper.like("content", "%" + map.get("content") + "%");
        }

        // 3.返回结果
        List<Article> articleList = articleMapper.selectPage(pageList, entityWrapper);
        return pageList.setRecords(articleList);
    }


    /**
     * @Description 订阅
     * @Author tangKai
     * @Date 14:01 2019/12/19
     * @Param [articleId, userId]
     * @Return boolean
     **/
    public boolean subscribe(String articleId, String userId) {
        // 根据文章id查询文章作者authorId
        String authorId = articleMapper.selectById(articleId).getUserid();

        // 用户key=userKey value=作者集合
        String userKey = "article_user_" + userId;

        // 作者key=authorKey value=用户集合
        String authorKey = "article_author_" + authorId;


        // 判断用户是否已经关注作者
        Boolean isMember = redisTemplate.opsForSet().isMember(userKey, authorId);

        // 让当前用户的消息队列 关注 routingKey(作者id) true:持久化
        Queue queue = new Queue("article_subscribe_" + userId, true);
        // 声明exchange的queue的绑定关系，设置路由键为作者id
        Binding binding = BindingBuilder.bind(queue).to(directExchange).with(authorId);

        if (isMember) {
            // 取消关注
            redisTemplate.opsForSet().remove(userKey, authorId);
            redisTemplate.opsForSet().remove(authorKey, userId);

            // 进行解绑
            rabbitAdmin.removeBinding(binding);
            return false;
        } else {
            // 产生订阅关系
            redisTemplate.opsForSet().add(userKey, authorId);
            redisTemplate.opsForSet().add(authorKey, userId);

            // 进行绑定
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);
            return true;
        }
    }


    /**
     * @Description 文章点赞
     * @Author tangKai
     * @Date 16:00 2019/12/19
     * @Param [articleId, userId]
     * @Return void
     **/
    public boolean thumbup(String articleId, String userId) {

        Object flag = redisTemplate.opsForValue().get("article_thumbup_userId:" + userId + "_articleId:" + articleId);
        if (StringUtils.isEmpty(flag)) {
            // 点赞
            Article article = articleMapper.selectById(articleId);
            article.setThumbup(article.getThumbup() + 1);
            articleMapper.updateById(article);

            // 消息通知
            Notice notice = new Notice();
            notice.setReceiverId(article.getUserid());
            notice.setOperatorId(userId);
            notice.setAction("thumbup");
            notice.setTargetType("article");
            notice.setTargetId(articleId);
            notice.setCreatetime(new Date());
            notice.setType("user");
            notice.setState("0");

            noticeClient.add(notice);
            // 设置点赞记录
            redisTemplate.opsForValue().set("article_thumbup_userId:" + userId + "_articleId:" + articleId, "1");

            // 1 创建队列，每个用户都有自己的队列，通过用户id进行区分
            Queue queue = new Queue("article_thumbup_" + article.getUserid(), true);
            rabbitAdmin.declareQueue(queue);

            // 2 发送消息
            rabbitTemplate.convertAndSend("article_thumbup_" + article.getUserid(), articleId);

            return true;
        } else {
            // 取消点赞
            Article article = articleMapper.selectById(articleId);
            article.setThumbup(article.getThumbup() - 1);
            articleMapper.updateById(article);

            // 删除待通知消息
            //noticeClient.freshDelete(noticeFresh);

            // 删除点赞记录
            redisTemplate.delete("article_thumbup_userId:" + userId + "_articleId:" + articleId);



            return false;
        }

    }


}
