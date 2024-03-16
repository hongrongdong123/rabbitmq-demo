package com.example.headers;

import com.example.headers.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo05HeadersApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Demo05HeadersApplication.class, args);
    }

    @Resource
    MessageService messageService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}
