package com.bjpowernode.controller;

import com.bjpowernode.dto.Result;
import com.bjpowernode.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pms/admin/oss")
public class AdminOSSController {

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        return minioUtil.upload(file);
    }

    /**
     * 删除文件
     * @param filename
     * @return
     */
    @DeleteMapping("/remove/{filename}")
    public Result deleteFile(@PathVariable String filename){
        return minioUtil.removeFile(filename);
    }
}
