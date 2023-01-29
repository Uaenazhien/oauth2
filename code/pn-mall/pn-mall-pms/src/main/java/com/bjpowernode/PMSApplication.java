package com.bjpowernode;

import com.bjpowernode.config.MinioConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.bjpowernode.mapper")
@Import({MinioConfig.class})
public class PMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(PMSApplication.class, args);
    }
}