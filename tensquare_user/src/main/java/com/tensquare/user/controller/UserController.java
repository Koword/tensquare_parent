package com.tensquare.user.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-12-02 22:58:20
 **/
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = GET)
    public Result selectById(@PathVariable(value = "userId") String userId) {
        User user = userService.SelectById(userId);
        return new Result(true, StatusCode.OK, "根据id查询用户成功!", user);
    }

}
