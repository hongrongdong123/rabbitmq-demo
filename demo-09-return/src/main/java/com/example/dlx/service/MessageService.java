package com.example.dlx.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
//    @Resource
//    private MyConfirmCallBack myConfirmCallBack;

//    RabbitTemplate.ReturnsCallback

    @PostConstruct //构造方法后执行它，相当于初始化作用
    public void init() {
        rabbitTemplate.setReturnsCallback(
                var->{
                    //log.error(var1.getMessage().toString());
                    log.error("" + var);
                }
        );
    }

    public void sendMsg() {
        Message message = MessageBuilder.withBody("hello world 1".getBytes()).build();
        CorrelationData correlationData = new CorrelationData();//关联数据
        correlationData.setId("order_123456");//发送订单信息
        rabbitTemplate.convertAndSend(exchangeName, "infoerr", message, correlationData);
        log.info("消息发送完毕:{}", new Date());

    }
}
