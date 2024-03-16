package com.example.reliability.message;

import com.example.reliability.vo.Orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

/**
 * description:
 */
@Component
@Slf4j
public class Receive {

    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Resource
    private ObjectMapper objectMapper;//这个对象可以进行序列化和反序列化（json）
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @RabbitListener(queues = {"queue.idempotent"})
    public void receiveMsg(Message message, Channel channel) throws Exception{

        MessageProperties messageProperties = message.getMessageProperties();
        //获取消息唯一标识
        long deliveryTag = messageProperties.getDeliveryTag();
        //使用objectmapper把字节数组反序列化成对象
        Orders orders = objectMapper.readValue(message.getBody(), Orders.class);
        try {
            log.info("接收到的信息为：{}", orders.toString());
            Boolean setResult = stringRedisTemplate.opsForValue().setIfAbsent("idempotent", orders.getId());
            if(setResult) {
                // TODO 插入数据库
                log.info("向数据库插入订单");
            }
            //手动确认
            channel.basicAck(deliveryTag, false);


        } catch (Exception e) {
            //throw new RuntimeException(e);
            try {
                log.error("接收者出现错误：{}", e.getMessage());
                //不确认，且不重新入队
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
