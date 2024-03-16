package com.example.topic.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * description:
 */
@Service
@Slf4j
public class MessageService {

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMsg() {
        Message message = MessageBuilder.withBody("hello world".getBytes()).build();
        amqpTemplate.convertAndSend("exchange.topic", "hello.word", message);
        log.info("消息发送完成");
    }
}
