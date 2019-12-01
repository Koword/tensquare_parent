package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ArticleController {

    @Autowired
    private ArticleService articleService;


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
     * @param articleId
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

    @RequestMapping(value = "/search/{page}/{size}", method = POST)
    public Result search(@RequestBody Map map,
                         @PathVariable(value = "page") int page,
                         @PathVariable(value = "size") int size) {
        Page<Article> pageList = articleService.search(map, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getRecords());
        return new Result(true, StatusCode.OK, "分页查询成功!", pageResult);
    }
}
