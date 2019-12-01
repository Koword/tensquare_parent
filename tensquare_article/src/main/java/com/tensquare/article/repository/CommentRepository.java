package com.tensquare.article.repository;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-12-01 13:53:40
 **/
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * 根据文章id查询评论
     * @param articleid
     * @return
     */
    List<Comment> findByArticleid(String articleid);

}
