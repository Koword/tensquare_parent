package com.tensquare.article.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 公共异常处理类
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-26 21:46:48
 **/
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    public Result error(Exception e) {
        String message = e.getMessage();
        // 举个栗子
        if (e instanceof ArithmeticException) {
            message = "除数不能为零";
        }
        return new Result(false, StatusCode.ERROR, message);
    }

}
