package com.example.demo02receivemsg.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * description:
 */
@Component
@Slf4j
public class ReceiveMessage {

    @RabbitListener(queues = {"queue.fanout.a", "queue.fanout.b"})
    public void receivrMsg(Message message) {
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("接收到的信息为：{}", msg);
    }
}
