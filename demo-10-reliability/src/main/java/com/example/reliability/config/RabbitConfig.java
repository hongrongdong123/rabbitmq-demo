package com.example.reliability.config;

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
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Value("${my.exchangeAlternateName}")
    private String exchangeAlternateName;
    @Value("${my.queueAlternateName}")
    private String queueAlternateName;

    //正常交换机
    @Bean
    public DirectExchange normalExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("alternate-exchange", exchangeAlternateName);
        return new DirectExchange(exchangeNormalName, true, false, arguments);
    }
    //正常队列
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable(queueNormalName).build();
    }
    //备份交换机,扇形交换机
    @Bean
    public FanoutExchange alternateExchange() {
        return ExchangeBuilder.fanoutExchange(exchangeAlternateName).build();
    }
    //备份队列
    @Bean
    public Queue alternateQueue() {
        return QueueBuilder.durable(queueAlternateName).build();
    }
    //正常交换机和正常队列绑定
    @Bean
    public Binding normalBinding(DirectExchange normalExchange, Queue normalQueue) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("info");
    }
    //备份交换机和备份队列绑定
    @Bean
    public Binding alternateBinding(FanoutExchange alternateExchange, Queue alternateQueue) {
        return BindingBuilder.bind(alternateQueue).to(alternateExchange);
    }
}
