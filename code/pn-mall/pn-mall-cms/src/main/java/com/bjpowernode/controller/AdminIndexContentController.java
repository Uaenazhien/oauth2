package com.bjpowernode.controller;

import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.IndexContent;
import com.bjpowernode.param.IndexContentPageQueryParam;
import com.bjpowernode.service.IndexContentService;
import com.bjpowernode.validation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/admin/indexContents")
public class AdminIndexContentController{
    @Autowired
    private IndexContentService indexContentService;

    /**
     * 分页查询
     * @param queryParam
     * @return
     */
    @GetMapping
    public Result<PageResult<IndexContent>> list(@Validated IndexContentPageQueryParam queryParam){
        return Result.success("请求成功",indexContentService.pageQuery(queryParam));
    }

    /**
     * 新增
     * @param indexContent
     * @return
     */
    @PostMapping
    public Result<Long> save(@Validated @RequestBody IndexContent indexContent){
        indexContentService.saveIndexContent(indexContent);
        return Result.success("首页内容新增成功",indexContent.getId());
    }

    /**
     * 修改
     * @param indexContent
     * @return
     */
    @PutMapping
    public Result updateById(@Validated(UpdateGroup.class) @RequestBody IndexContent indexContent){
        indexContentService.updateIndexContent(indexContent);
        return  Result.success("首页内容修改成功");

    }

    /**
     * 删除
     * @param indexContentId
     * @return
     */
    @DeleteMapping("/{indexContentId}")
    public Result removeById(@PathVariable Long indexContentId){
        indexContentService.removeIndexContentById(indexContentId);
        return Result.success("删除首页内容删除成功");
    }
}