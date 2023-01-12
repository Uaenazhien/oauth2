package com.bjpowernode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bjpowernode.mapper")
public class PMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(PMSApplication.class, args);
    }
}