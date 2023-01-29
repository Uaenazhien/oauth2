package com.bjpowernode.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.bjpowernode.config.MinioConfig;
import com.bjpowernode.dto.Result;
import com.bjpowernode.exception.BizException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Component
public class MinioUtil {
    @Autowired(required = false)
    private MinioClient minioClient;

    @Autowired(required = false)
    private MinioConfig minioConfig;

    //上传成功后的 文件路径 文件名称 返回给客户端
    public Result upload(MultipartFile file) {

        try {
            //拿到文件后缀
            //生成新的对象名称
            String objectName = IdUtil.simpleUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
            //文件上传
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            HashMap<String, String> data = new HashMap<>();

            data.put("url", minioConfig.getEndpoint() + "/" + minioConfig.getBucket() + "/" + objectName);


            data.put("name", objectName);
            return Result.success("上传成功", data);

        } catch (Exception e) {
            throw new BizException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public Result removeFile(String fileName) {
        try {
            String bucket = minioConfig.getBucket();
            minioClient.removeObject(RemoveObjectArgs
                    .builder().bucket(bucket).object(fileName).build());
            return Result.success("删除成功");
        } catch (Exception e) {
            throw new BizException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

}
