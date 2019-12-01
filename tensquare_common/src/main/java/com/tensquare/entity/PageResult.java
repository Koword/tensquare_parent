package com.tensquare.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageResult
 * @Description 分页返回
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-12 19:18:13
 * @Version 1.0
 **/
@Data
public class PageResult<T> implements Serializable {

    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

}
