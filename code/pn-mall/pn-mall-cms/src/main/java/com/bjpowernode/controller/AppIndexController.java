package com.bjpowernode.controller;

import com.bjpowernode.Feign.FeignPmsService;
import com.bjpowernode.dto.CategoryDTO;
import com.bjpowernode.dto.ProductDTO;
import com.bjpowernode.dto.Result;
import com.bjpowernode.service.IndexCarouselService;
import com.bjpowernode.service.IndexContentService;
import com.bjpowernode.vo.IndexCarouselVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms/app/index")
public class AppIndexController {

    @Autowired
    private IndexCarouselService indexCarouselService;

    @Autowired
    private IndexContentService indexContentService;

    @Autowired
    private FeignPmsService feignPmsService;

    @GetMapping("/carousels/{num}")
    public Result<List<IndexCarouselVO>> getCarouselsForIndex(@PathVariable Integer num) {
        return Result.success("请求成功", indexCarouselService.getCarouselsForIndex(num));
    }

    @GetMapping("/contents/{contentType}/{num}")
    public Result<List<ProductDTO>> getProductsForIndex(@PathVariable Integer contentType,
                                                        @PathVariable Integer num) {
        return indexContentService.getContentsForIndex(contentType, num);

    }

    /**
     * 获取首页商品分类（一级分类）
     * @return
     */
    @GetMapping("/categorys/level1")
    public Result<List<CategoryDTO>> getCategorysForIndex() {
        return feignPmsService.listByLevel(1L);
    }
}
