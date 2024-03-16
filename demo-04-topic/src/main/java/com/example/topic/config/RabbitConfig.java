package com.example.topic.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 */
@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueAName}")
    private String queueAName;
    @Value("${my.queueBName}")
    private String queueBName;

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(exchangeName).build();
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
    public Binding bindingA(TopicExchange topicExchange, Queue queueA) {
        return BindingBuilder.bind(queueA).to(topicExchange).with(("*.orange.*"));
    }
    @Bean
    public Binding bindingB1(TopicExchange topicExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(topicExchange).with(("*.*.rabbit"));
    }
    @Bean
    public Binding bindingB2(TopicExchange topicExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(topicExchange).with(("lazy.#"));
    }
}
