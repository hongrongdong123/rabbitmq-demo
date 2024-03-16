package com.example.reliability.service;

import com.example.reliability.vo.Orders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;
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
    @Resource
    private ObjectMapper objectMapper;//这个对象可以进行序列化和反序列化（json）


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

    public void senMsg() throws JsonProcessingException {
        {
            Orders orders1 = Orders.builder()
                    .id("order_12345")
                    .orderName("买的手机")
                    .orderMoney(new BigDecimal(2356))
                    .orderTime(new Date())
                    .build();
            String strOrder1 = objectMapper.writeValueAsString(orders1);
            Message message = MessageBuilder.withBody(strOrder1.getBytes()).build();
            rabbitTemplate.convertAndSend(exchangeNormalName, "info", message);
            log.info("发送消息完毕，发送时间为：{}", new Date());
        }


        Orders orders2 = Orders.builder()
                .id("order_12345")
                .orderName("买的手机")
                .orderMoney(new BigDecimal(2356))
                .orderTime(new Date())
                .build();
        String strOrder2 = objectMapper.writeValueAsString(orders2);
        Message message = MessageBuilder.withBody(strOrder2.getBytes()).build();
        rabbitTemplate.convertAndSend(exchangeNormalName, "info", message);
        log.info("发送消息完毕，发送时间为：{}", new Date());

    }
}
