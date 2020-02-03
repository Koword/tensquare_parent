package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-24 23:56:25
 **/
@RestController
@RequestMapping("/article")
@CrossOrigin
@AllArgsConstructor
public class ArticleController {


    ArticleService articleService;

    RedisTemplate redisTemplate;


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 查询全部
     * @Date: 2019-11-25 0025 0:25
     * @Param * @param
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(method = GET)
    public Result selectAll() {
        List<Article> articles = articleService.selectAll();
        return new Result(true, StatusCode.OK, "查询所有文章成功!", articles);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据id查询成功
     * @Date: 2019-11-25 0025 0:26
     * @Param * @param articleId
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/{articleId}", method = GET)
    public Result selectById(@PathVariable(value = "articleId") String articleId) {
        Article article = articleService.selectById(articleId);
        return new Result(true, StatusCode.OK, "根据id查询文章成功!", article);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 新增
     * @Date: 2019-11-25 0025 0:26
     * @Param * @param article
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(method = POST)
    public Result insert(@RequestBody Article article) {
        articleService.insert(article);
        return new Result(true, StatusCode.OK, "新增文章成功!");
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 更新
     * @Date: 2019-11-25 0025 0:35
     * @Param * @param article
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/{articleId}", method = PUT)
    public Result updateById(@RequestBody Article article, @PathVariable(value = "articleId") String articleId) {
        // 以URL中的articleId为准
        article.setId(articleId);
        articleService.updateById(article);
        return new Result(true, StatusCode.OK, "修改文章成功!");
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 删除文章
     * @Date: 2019-11-26 0026 20:10
     * @Param * @param articleId
     * @Return: com.tensquare.entity.Result
     */
    @RequestMapping(value = "/{articleId}", method = DELETE)
    public Result deleteById(@PathVariable(value = "articleId") String articleId) {
        articleService.deleteById(articleId);
        return new Result(true, StatusCode.OK, "删除文章成功!");
    }


    /**
     * @Description 分页查询
     * @Author tangkai
     * @Date 14:38 2019/12/19
     * @Param [map, page, size]
     * @Return com.tensquare.entity.Result
     **/
    @RequestMapping(value = "/search/{page}/{size}", method = POST)
    public Result search(@RequestBody Map map,
        @PathVariable(value = "page") int page,
        @PathVariable(value = "size") int size) {
        Page<Article> pageList = articleService.search(map, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getRecords());
        return new Result(true, StatusCode.OK, "分页查询成功!", pageResult);
    }


    /**
     * @Description 订阅
     * @Author tangkai
     * @Date 14:56 2019/12/19
     * @Param [map]
     * @Return com.tensquare.entity.Result
     **/
    @PostMapping(value = "/subscribe",consumes = "application/json")
    public Result subscribe(@RequestBody Map map) {
        String articleId = (String) map.get("articleId");
        String userId = (String) map.get("userId");
        boolean flag = articleService.subscribe(articleId, userId);

        if (flag) {
            return new Result(true, StatusCode.OK, "订阅成功!");
        }
        return new Result(true, StatusCode.OK, "取消订阅成功!");

    }


    /**
     * @Description 文章点赞
     * @Author tangKai
     * @Date 16:18 2019/12/19
     * @Param [articleId]
     * @Return com.tensquare.entity.Result
     **/
    @RequestMapping(value = "/thumbup/{articleId}/{userId}", method = PUT)
    public Result thumbup(@PathVariable(value = "articleId") String articleId,
                          @PathVariable(value = "userId") String userId) {

        boolean flag = articleService.thumbup(articleId, userId);
        if (flag) {
            // 点赞成功
            return new Result(true, StatusCode.OK, "点赞成功!");
        }
        // 取消点赞
        return new Result(true, StatusCode.OK, "取消点赞成功!");
    }


}
