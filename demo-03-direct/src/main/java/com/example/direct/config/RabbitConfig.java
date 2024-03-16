package com.example.direct.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 */
@Configuration
//@ConfigurationProperties(prefix = "my")
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueAName}")
    private String queueAName;
    @Value("${my.queueBName}")
    private String queueBName;


    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queueA() {
        return QueueBuilder.durable(queueAName).build();
    }
    @Bean
    public Queue queueB() {
        return QueueBuilder.durable(queueBName).build();
    }

    @Bean
    public Binding bindingA(DirectExchange directExchange, Queue queueA) {
        return BindingBuilder.bind(queueA).to(directExchange).with("info");
    }
    @Bean
    public Binding bindingB1(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("info");
    }
    @Bean
    public Binding bindingB2(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("error");
    }
    @Bean
    public Binding bindingB3(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("warning");
    }
}
