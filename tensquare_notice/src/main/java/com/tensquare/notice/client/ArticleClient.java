package com.tensquare.notice.client;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 文章接口，根据文章id查询文章信息
 * @Author tangkai
 * @Date 11:39 2019/12/19
 **/
@FeignClient("tensquare-article")
public interface ArticleClient {

    @RequestMapping(value = "/article/{articleId}", method = GET)
    Result selectById(@PathVariable(value = "articleId") String articleId);
}
