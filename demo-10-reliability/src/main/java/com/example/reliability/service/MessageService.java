package com.example.reliability.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * description:
 */
@Component
@Slf4j
public class MessageService {
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Resource
    private RabbitTemplate rabbitTemplate;

    //RabbitTemplate.ConfirmCallback
    //RabbitTemplate.ReturnsCallback

    /**
     * Confirm确认模式，确保消息发送到正确的交换机
     * return模式，确保交换机路由到正确的队列
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(
                (message, ack, cause)->{
                    if (ack) {
                        log.info("消息成功发送到交换机, 时间为：{}",  new Date());
                    } else {
                        log.error(cause.toString());
                    }
                }
        );
        rabbitTemplate.setReturnsCallback(
                (returnedMessage)->{
                    log.error(returnedMessage.toString());
                }
        );

    }

    public void senMsg() {
        Message message = MessageBuilder.withBody("test reliability".getBytes()).setMessageId("order123456").build();
        rabbitTemplate.convertAndSend(exchangeNormalName, "info", message);
        log.info("发送消息完毕，发送时间为：{}", new Date());
    }
}
