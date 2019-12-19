package com.tensquare.article.service;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.repository.CommentRepository;
import com.tensquare.util.IdWorker;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-12-01 13:54:59
 **/
@Service
public class CommentService {

    @Autowired
    IdWorker idWorker;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 新增
     * @Date: 2019-12-1 0001 13:58
     * @Param * @param comment
     * @Return: void
     */
    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        // 初始化数据
        comment.setPublishdate(new Date());
        comment.setThumbup(0);
        commentRepository.save(comment);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 修改
     * @Date: 2019-12-1 0001 14:07
     * @Param * @param comment
     * @Return: void
     */
    public void updateById(Comment comment) {
        commentRepository.save(comment);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 删除
     * @Date: 2019-12-1 0001 14:23
     * @Param * @param id
     * @Return: void
     */
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 查询全部评论
     * @Date: 2019-12-1 0001 14:42
     * @Param * @param
     * @Return: java.util.List<com.tensquare.article.pojo.Comment>
     */
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据id查询评论
     * @Date: 2019-12-1 0001 14:50
     * @Param * @param id
     * @Return: com.tensquare.article.pojo.Comment
     */
    public Comment findById(String id) {
        return commentRepository.findById(id).get();
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据文章id查询评论
     * @Date: 2019-12-1 0001 22:49
     * @Param * @param ArticleId
     * @Return: java.util.List<com.tensquare.article.pojo.Comment>
     */
    public List<Comment> findByArticleid(String articleid) {
        return commentRepository.findByArticleid(articleid);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 评论点赞
     * @Date: 2019-12-1 0001 23:30
     * @Param * @param id
     * @Return: void
     */
    public void thumbupPlus(String id) {

        // 更新的条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        // 没有点赞，就点赞
        // 更新的值
        Update updatePlus = new Update();
        updatePlus.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, updatePlus, "comment");
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 评论取消点赞
     * @Date: 2019-12-1 0001 23:30
     * @Param * @param id
     * @Return: void
     */
    public void thumbupReduce(String id) {

        // 更新的条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        // 已经点赞，就取消点赞
        // 更新的值
        Update updateReduce = new Update();
        updateReduce.inc("thumbup", -1);
        mongoTemplate.updateFirst(query, updateReduce, "comment");
    }
}
