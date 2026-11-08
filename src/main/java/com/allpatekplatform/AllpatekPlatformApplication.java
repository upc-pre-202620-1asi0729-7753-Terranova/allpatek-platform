package com.allpatekplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AllpatekPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllpatekPlatformApplication.class, args);
    }
}
