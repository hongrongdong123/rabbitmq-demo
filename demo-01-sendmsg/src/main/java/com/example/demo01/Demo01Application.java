package com.example.demo01;

import com.example.demo01.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo01Application implements ApplicationRunner {
    @Resource
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication.run(Demo01Application.class, args);
    }


    /**
     * 程序已启动就运行该方法
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}
