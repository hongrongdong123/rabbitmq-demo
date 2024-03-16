package com.example.ttl01;

import com.example.ttl01.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo06Ttl01Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Demo06Ttl01Application.class, args);
    }

    @Resource
    MessageService messageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsgTtl();
    }
}
