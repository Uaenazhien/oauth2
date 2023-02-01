package com.bjpowernode.controller;

import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductQueryParam;
import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.Product;
import com.bjpowernode.enums.ProductStatus;
import com.bjpowernode.service.ProductService;
import com.bjpowernode.validation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品后台管理接口
 */

@RestController
@RequestMapping("/pms/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    /**
     * 分页查询商品数据
     *
     * @param queryParam
     * @return
     */
    @GetMapping
    public Result<PageResult<Product>> list(@Validated ProductQueryParam queryParam) {
        return Result.success("请求成功", productService.pageQuery(queryParam));

    }

    /**
     * 根据商品id查询商品信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public Result<Product> getProductById(@PathVariable Long productId) {

        Product product = productService.getProductById(productId);
        return Result.success("获取商品信息成功", product);
    }

    /**
     * 新增商品
     *
     * @param product
     * @return
     */
    @PostMapping
    public Result<Long> saveProduct(@Validated @RequestBody Product product) {
        productService.saveProduct(product);
        return Result.success("新增商品成功", product.getId());
    }

    /**
     * 修改商品
     * @param product
     * @return
     */
    @PutMapping
    public Result updateById(@RequestBody @Validated(UpdateGroup.class) Product product) {
        productService.updateProduct(product);
        return Result.success("商品修改成功");
    }
    /**
     * 删除商品
     *
     * @param
     * @return
     */
    @DeleteMapping("/{productId}")
    public Result remove(@PathVariable("productId") Long productId) {
        productService.removeProductById(productId);
        return Result.success("商品删除成功");
    }
    @PutMapping("/status/{productId}/{status}")
    public Result updateProductStatus(@PathVariable("productId") Long productId,
                                      @PathVariable("status") Integer status){
        productService.updateProductStatus(productId,status);
        if (ProductStatus.ON.getStatus() == status){
            return Result.success("商品上架成功");
        }
        return Result.success("商品下架成功");
    }
}
