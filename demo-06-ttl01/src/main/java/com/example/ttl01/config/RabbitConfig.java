package com.example.ttl01.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue queueTtl() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 15000);//消息过期时间
        return QueueBuilder.durable("queueTtl")
                .withArguments(arguments)
                .build();
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queueTtl) {
        return BindingBuilder.bind(queueTtl()).to(directExchange).with("info");
    }
}
