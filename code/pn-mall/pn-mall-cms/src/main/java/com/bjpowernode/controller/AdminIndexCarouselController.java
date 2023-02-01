package com.bjpowernode.controller;

import com.bjpowernode.dto.PageParam;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.IndexCarousel;
import com.bjpowernode.service.IndexCarouselService;
import com.bjpowernode.util.MinioUtil;
import com.bjpowernode.validation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cms/admin/indexCarousels")
public class AdminIndexCarouselController {
    @Autowired
    private IndexCarouselService indexCarouselService;

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 分页查询
     * @param queryParam
     * @return
     */
    @GetMapping
    public Result<PageResult<IndexCarousel>> list(@Validated PageParam queryParam) {
        return Result.success("请求成功", indexCarouselService.pageQuery(queryParam));
    }

    /**
     * 新增轮播图
     * @param indexCarousel
     * @return
     */
    @PostMapping
    public Result<Long> save(@RequestBody @Validated IndexCarousel indexCarousel) {
        indexCarouselService.save(indexCarousel);
        return Result.success("新增轮播图成功",indexCarousel.getId());
    }

    /**
     * 修改首页轮播图
     * @param indexCarousel
     * @return
     */
    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) IndexCarousel indexCarousel){
        indexCarouselService.updateIndexCarousel(indexCarousel);
        return Result.success("修改首页轮播图成功");
    }

    /**
     * 删除首页轮播图
     * @param indexCarouseId
     * @return
     */
    @DeleteMapping("/{indexCarouseId}")
    public Result del(@PathVariable Long indexCarouseId){
        indexCarouselService.removeIndexCarousel(indexCarouseId);
        return Result.success("删除轮播图成功");
    }

    /**
     * 上传轮播图到minio
     */

    @PostMapping("/img/upload")
    public Result upload(MultipartFile file){
        return minioUtil.upload(file);
    }

    /**
     * 删除轮播图
     * @param filename
     * @return
     */
    @DeleteMapping("/img/remove/{filename}")
    public Result removeFile(@PathVariable String filename){
        return minioUtil.removeFile(filename);
    }
}