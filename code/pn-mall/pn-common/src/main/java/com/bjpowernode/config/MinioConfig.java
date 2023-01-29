package com.bjpowernode.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Data
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint ;

    @Value("${minio.accessKey}")
    private String accessKey ;

    @Value("${minio.secretKey}")
    private String secretKey ;

    @Value("${minio.bucket}")
    private String bucket;

    @Bean
    public MinioClient minioClient(){

        //初始化MinioClient对象
        return  MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
    }


}