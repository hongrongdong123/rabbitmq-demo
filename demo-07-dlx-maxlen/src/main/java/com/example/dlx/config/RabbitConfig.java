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
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Value("${my.exchangeDlxName}")
    private String exchangeDlxName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    //正常交换机和正常队列
    @Bean
    public DirectExchange normalExchange() {
        return ExchangeBuilder.directExchange(exchangeNormalName).build();
    }
    @Bean
    public Queue normalQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        //arguments.put("x-message-ttl", 20000);//设置队列消息过期时间
        arguments.put("x-dead-letter-exchange", exchangeDlxName);//设置消息过期后发送到哪个交换机
        arguments.put("x-dead-letter-routing-key", "error");//发给死信交换机带的路由key，应该和死信交换机与死信队列绑定的路由key一致
        arguments.put("x-max-length", 5);//设置队列最大长度
        return QueueBuilder.durable(queueNormalName)
                .withArguments(arguments)
                .build();
    }
    @Bean
    public Binding bindingNormal(DirectExchange normalExchange, Queue normalQueue) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("order");
    }

    //死信交换机和死信队列
    @Bean
    public DirectExchange dlxExchange() {
        return ExchangeBuilder.directExchange(exchangeDlxName).build();
    }
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(queueDlxName).build();
    }
    @Bean
    public Binding bindingDlx(DirectExchange dlxExchange, Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("error");
    }


}
