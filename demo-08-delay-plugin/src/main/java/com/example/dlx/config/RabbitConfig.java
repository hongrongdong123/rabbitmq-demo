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
    @Value("${my.queueDelayName}")
    private String queueDelayName;

    //自定义交换机
    @Bean
    public CustomExchange customExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(exchangeName, "x-delayed-message", true, false, arguments);
    }
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueDelayName)
                .build();
    }
    @Bean
    public Binding binding(CustomExchange customExchange, Queue queue) {
        //绑定
        return BindingBuilder.bind(queue).to(customExchange).with("plugin").noargs();
    }



}
