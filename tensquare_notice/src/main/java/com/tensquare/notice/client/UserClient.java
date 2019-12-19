package com.tensquare.notice.client;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 用户微服务接口，通过此接口获取用户昵称
 * @Author tangkai
 * @Date 11:45 2019/12/19
 **/
@FeignClient("tensquare-user")
public interface UserClient {

    @RequestMapping(value = "/user/{userId}", method = GET)
    Result selectById(@PathVariable(value = "userId") String userId);


}
