package com.example.dlx;

import com.example.dlx.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class delayApp implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(delayApp.class, args);
    }

    @Resource
    private MessageService messageService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}
