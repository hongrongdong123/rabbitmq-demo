package com.example.direct.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * description:
 */
@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        Message message = MessageBuilder.withBody("hello world".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.direct", "info", message);
        log.info("消息发送完毕,发送时间为: {}", new Date());
    }
}
