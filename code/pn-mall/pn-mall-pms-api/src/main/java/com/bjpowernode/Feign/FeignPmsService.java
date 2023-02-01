package com.bjpowernode.Feign;

import com.bjpowernode.dto.CategoryDTO;
import com.bjpowernode.dto.ProductDTO;
import com.bjpowernode.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "pn-mall-pms", path = "/pms/app")
public interface FeignPmsService {
    @GetMapping("/products/listByIds")
    Result<List<ProductDTO>> listProductByProductIds(@RequestParam("productIds") List<Long> productIds);

    @GetMapping(value = "/categorys/listByLevel")
    Result<List<CategoryDTO>> listByLevel(@RequestParam(value = "level") Long level);
}
