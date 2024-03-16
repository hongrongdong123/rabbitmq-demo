package com.example.reliability.message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * description:
 */
@Component
@Slf4j
public class Receive {

    @Value("${my.queueNormalName}")
    private String queueNormalName;

    @RabbitListener(queues = {"queue.reliability"})
    public void receiveMsg(Message message, Channel channel) {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            byte[] body = message.getBody();
            String msg = new String(body);
            int a = 10 / 0;
            //手动确认
            channel.basicAck(deliveryTag, false);
            log.info("接收到的信息为：{}", msg);

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
