package com.tensquare.article.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@AllArgsConstructor
public class RabbitmqConfig {

    public static  final String EX_ARTICLE = "article_subscribe";

    private RabbitTemplate rabbitTemplate;

    @Bean
    public RabbitAdmin rabbitAdmin(){
        return new RabbitAdmin(rabbitTemplate.getConnectionFactory());
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EX_ARTICLE);
    }
}
