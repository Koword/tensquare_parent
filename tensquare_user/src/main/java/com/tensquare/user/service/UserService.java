package com.tensquare.user.service;

import com.tensquare.user.dao.UserMapper;
import com.tensquare.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-12-02 22:56:18
 **/
@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据id查询用户
     * @Date:  2019-12-2 0002 22:57
     * @Param  * @param userId
     * @Return: com.tensquare.user.pojo.User
     */
    public User SelectById(String userId) {
        return userMapper.selectById(userId);
    }
}
