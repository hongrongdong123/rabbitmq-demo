package com.example.reliability;

import com.example.reliability.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReliabilityApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReliabilityApplication.class, args);
    }

    @Resource
    private MessageService messageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.senMsg();
    }
}
