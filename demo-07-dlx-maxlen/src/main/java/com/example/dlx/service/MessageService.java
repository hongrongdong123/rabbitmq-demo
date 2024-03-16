package com.example.dlx.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
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

        for (int i = 1; i <= 8 ; i++) {
            String str = "hello world" + i;
            Message message = MessageBuilder.withBody(str.getBytes()).build();
            rabbitTemplate.convertAndSend("exchange.normal.2", "order", message);
        }

        log.info("消息发送完毕:{}", new Date());
    }
}
