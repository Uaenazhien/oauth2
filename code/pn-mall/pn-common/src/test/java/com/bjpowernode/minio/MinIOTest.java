package com.bjpowernode.minio;

import io.minio.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;


public class MinIOTest{
    /**
     * 桶操作
     *
     * @throws Exception
     */
    @Test
    void makeBucket() throws Exception{
        String endpoint = "http://192.168.100.41:9000";
        String accessKey = "minioadmin";
        String secretKey = "minioadmin";
        //初始化MinioClient对象
        MinioClient minioClient = MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
        //创建桶
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("test").build());
        // 删除桶
        //minioClient.removeBucket(RemoveBucketArgs.builder().bucket("test").build());
    }

    /**
     * 上传本地文件
     * @throws Exception
     */
    @Test
    void upload() throws Exception{
        String endpoint = "http://192.168.100.41:9000";
        String accessKey = "minioadmin";
        String secretKey = "minioadmin";
        //初始化MinioClient对象
        MinioClient minioClient = MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
        minioClient.uploadObject(UploadObjectArgs.builder()
                        .bucket("test")
                        .filename("D:\\IU.jpg")
                        //对象名称
                        .object("iu.jpg")
                .build());
        // 文件删除
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("test")
                .object("i-mall.sql")
                .build());
    }



        /**
     * 文件操作
     *
     * @throws Exception
     */
    @Test
    void upload2() throws Exception{
        String endpoint = "http://192.168.100.41:9000";
        String accessKey = "minioadmin";
        String secretKey = "minioadmin";
        //初始化MinioClient对象
        MinioClient minioClient = MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
        File file = new File("D:\\IU.jpg");
        minioClient.putObject(PutObjectArgs.builder()
                        .bucket("test")
                        .object("1.jpg")
                        .stream(new FileInputStream(file),file.length(),-1)
                .build());

        // 文件删除
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("test")
                .object("1.jpg")
                .build());
    }
}