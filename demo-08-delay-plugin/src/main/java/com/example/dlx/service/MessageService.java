package com.example.dlx.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
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
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", 25000);
            //messageProperties.setExpiration("25000");
            Message message = MessageBuilder.withBody("hello world 1".getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend(exchangeName, "plugin", message);
            log.info("消息发送完毕:{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", 15000);
            //messageProperties.setExpiration("15000");
            Message message = MessageBuilder.withBody("hello world 2".getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend(exchangeName, "plugin", message);
            log.info("消息发送完毕:{}", new Date());
        }
    }
}
