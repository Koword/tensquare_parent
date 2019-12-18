package com.tensquare.notice.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author tangkai
 * @Date 12:00 2019/12/18
 **/
@Configuration
//配置Mapper包扫描
@MapperScan("com.tensquare.notice.dao")
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor createPaginationInterceptor() {
        return new PaginationInterceptor();
    }
}
