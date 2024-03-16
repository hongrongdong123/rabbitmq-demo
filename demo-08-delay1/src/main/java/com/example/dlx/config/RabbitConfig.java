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
    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    //正常交换机和正常队列
    @Bean
    public DirectExchange Exchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue normalQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 25000);//设置队列消息过期时间
        arguments.put("x-dead-letter-exchange", exchangeName);//设置消息过期后发送到哪个交换机
        arguments.put("x-dead-letter-routing-key", "error");//发给死信交换机带的路由key，应该和死信交换机与死信队列绑定的路由key一致
        return QueueBuilder.durable(queueNormalName)
                .withArguments(arguments)
                .build();
    }
    @Bean
    public Binding bindingNormal(DirectExchange Exchange, Queue normalQueue) {
        return BindingBuilder.bind(normalQueue).to(Exchange).with("order");
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(queueDlxName).build();
    }
    @Bean
    public Binding bindingDlx(DirectExchange Exchange, Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(Exchange).with("error");
    }


}
