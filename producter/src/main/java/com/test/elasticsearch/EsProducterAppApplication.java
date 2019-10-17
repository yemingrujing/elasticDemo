package com.test.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EsProducterAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsProducterAppApplication.class, args);
    }
}