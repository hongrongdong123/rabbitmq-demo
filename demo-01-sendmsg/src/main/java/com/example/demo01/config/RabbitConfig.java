package com.example.demo01.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 */
@Configuration
public class RabbitConfig {

    //三部曲
    //1.定义交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange.fanout");
    }
    //2.定义队列
    @Bean
    public Queue queueA() {
        return new Queue("queue.fanout.a");
    }
    @Bean
    public Queue queueB() {
        return new Queue("queue.fanout.b");
    }
    //3.绑定交换机和队列
    @Bean
    public Binding bingingA(FanoutExchange fanoutExchange, Queue queueA) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }
    @Bean
    public Binding bingingB(FanoutExchange fanoutExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
}
