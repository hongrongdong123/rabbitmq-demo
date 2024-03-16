//package com.example.dlx.message;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * description:延迟插件
// */
//@Component
//@Slf4j
//public class ReceiveMessage {
//
//    @RabbitListener(queues = {"queue.delay.4"})
//    public void receivrMsg(Message message) {
//        byte[] body = message.getBody();
//        String msg = new String(body);
//        log.info("接收到的信息为：{},接收时间为：{}", msg, new Date());
//    }
//}
