package com.tensquare.article;


import com.tensquare.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName ArticleApplication
 * @Description 文章启动类
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-19 20:23:06
 * @Version 1.0
 **/
@SpringBootApplication
//Mapper扫描注解
@MapperScan("com.tensquare.article.dao")
@EnableEurekaClient
@EnableFeignClients
public class    ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }
}
