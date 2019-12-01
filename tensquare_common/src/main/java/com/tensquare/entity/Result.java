package com.tensquare.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Description 返回结果
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-12 19:15:38
 * @Version 1.0
 **/
@Data
public class Result implements Serializable {

    private boolean flag;//是否成功
    private Integer code;// 返回码
    private String message;//返回信息
    private Object data;// 返回数据

    public Result() {
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
