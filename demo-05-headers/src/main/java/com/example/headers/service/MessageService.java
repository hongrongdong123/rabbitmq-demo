package com.example.headers.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * description:
 */
@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        //消息属性
        MessageProperties messageProperties = new MessageProperties();
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("type", "s");
        headers.put("status", 0);
        //设置消息头
        messageProperties.setHeaders(headers);
        //添加消息属性
        Message message = MessageBuilder.withBody("hello word".getBytes()).andProperties(messageProperties).build();
        //头部交换机
        rabbitTemplate.convertAndSend("exchange.header", "", message);
        log.info("消息发送完毕");

    }
}
