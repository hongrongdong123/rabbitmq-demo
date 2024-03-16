package com.example.demo01.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * description:
 */
@Component
@Slf4j
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        String msg = "hello world";
        Message message = new Message(msg.getBytes());
        rabbitTemplate.convertAndSend("exchange.fanout", "", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
