package com.example.dlx.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * description:
 */
@Component
@Slf4j
public class MessageService {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        Message message = MessageBuilder.withBody("hello world".getBytes()).build();
        rabbitTemplate.convertAndSend(exchangeName, "order", message);
        log.info("消息发送完毕:{}", new Date());
    }
}
