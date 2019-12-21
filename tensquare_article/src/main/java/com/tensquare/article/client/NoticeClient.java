package com.tensquare.article.client;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.tensquare.article.pojo.Notice;
import com.tensquare.article.pojo.NoticeFresh;
import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author tangkai
 * @Date 17:18 2019/12/18
 **/
@FeignClient("tensquare-notice")
public interface NoticeClient {

    @RequestMapping(value = "/notice",method = POST)
    Result add(@RequestBody Notice notice);

    @RequestMapping(value = "/notice/fresh",method = DELETE)
    Result freshDelete(@RequestBody NoticeFresh noticeFresh);

}
