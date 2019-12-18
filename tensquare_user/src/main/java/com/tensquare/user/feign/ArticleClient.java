package com.tensquare.user.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description
 * @Author tangkai
 * @Date 17:18 2019/12/18
 **/
@FeignClient("tensquare_article")
public interface ArticleClient {


}
