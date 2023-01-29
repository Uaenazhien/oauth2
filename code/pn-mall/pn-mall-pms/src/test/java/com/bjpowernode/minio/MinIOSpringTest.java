package com.bjpowernode.minio;

import cn.hutool.core.util.IdUtil;
import com.bjpowernode.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MinIOSpringTest {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Test
    void test() throws Exception{
        minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .filename("D:\\IU.jpg")
                //对象名称
                .object(IdUtil.simpleUUID())
                .build());
    }
}
