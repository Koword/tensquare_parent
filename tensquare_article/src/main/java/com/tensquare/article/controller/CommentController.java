package com.tensquare.article.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-12-01 13:59:52
 **/
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 新增
     * @Date: 2019-12-1 0001 14:03
     * @Param * @param comment
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(method = POST)
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "新增评论成功!");
    }

    /**
     * @param id
     * @Author: GaoLeng_Tang 🍭
     * @Description: 修改
     * @Date: 2019-12-1 0001 14:08
     * @Param * @param comment
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public Result update(@RequestBody Comment comment,
                         @PathVariable(value = "id") String id) {
        comment.set_id(id);
        commentService.updateById(comment);
        return new Result(true, StatusCode.OK, "修改评论成功!");
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 删除
     * @Date: 2019-12-1 0001 14:52
     * @Param * @param id
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public Result deleteById(@PathVariable(value = "id") String id) {
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除评论成功!");
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 查询全部评论
     * @Date: 2019-12-1 0001 14:52
     * @Param * @param
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(method = GET)
    public Result findAll() {
        List<Comment> commentList = commentService.findAll();
        return new Result(true, StatusCode.OK, "查询全部评论成功!", commentList);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据id查询评论
     * @Date: 2019-12-1 0001 14:55
     * @Param * @param id
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/{id}", method = GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Comment comment = commentService.findById(id);
        return new Result(true, StatusCode.OK, "根据id查询评论!", comment);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据文章id查询评论
     * @Date: 2019-12-2 0002 0:59
     * @Param * @param articleid
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/article/{articleid}", method = GET)
    public Result findByArticleId(@PathVariable(value = "articleid") String articleid) {
        List<Comment> list = commentService.findByArticleid(articleid);
        return new Result(true, StatusCode.OK, "根据文章查询评论成功!", list);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 评论点赞
     * @Date: 2019-12-1 0001 23:33
     * @Param * @param id
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/thumbup/{id}", method = PUT)
    public Result thumbup(@PathVariable(value = "id") String id) {

        String userId = "chris";
        Object flag = redisTemplate.boundValueOps("thumbup_userid:" + userId + "_id:" + id).get();
        if (!StringUtils.isEmpty(flag)) {
            redisTemplate.delete("thumbup_userid:" + userId + "_id:" + id);
            commentService.thumbupReduce(id);
            return new Result(true, StatusCode.OK, "取消点赞");
        }
        commentService.thumbupPlus(id);
        redisTemplate.boundValueOps("thumbup_userid:" + userId + "_id:" + id).set("1");
        return new Result(true, StatusCode.OK, "点赞成功!");
    }

}
