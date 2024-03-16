package com.example.topic;

import com.example.topic.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo04TopicApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Demo04TopicApplication.class, args);
    }


    @Resource
    MessageService messageService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}
