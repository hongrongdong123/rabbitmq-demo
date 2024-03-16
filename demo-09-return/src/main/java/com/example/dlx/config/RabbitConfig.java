package com.example.dlx.config;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * description:
 */
@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueName}")
    private String queueName;

    //自定义交换机
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName)
                .build();
    }
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        //绑定
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }



}
