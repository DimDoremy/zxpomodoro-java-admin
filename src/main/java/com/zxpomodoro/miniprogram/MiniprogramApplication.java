package com.zxpomodoro.miniprogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MiniprogramApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniprogramApplication.class, args);
        log.info("Springboot On!");
    }

}
