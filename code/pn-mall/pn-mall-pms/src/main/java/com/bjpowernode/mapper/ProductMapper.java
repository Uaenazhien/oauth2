package com.bjpowernode.mapper;

import com.bjpowernode.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 根据商品id集合获取商品数据，并按productIds集合的顺序排序
     * @param productIds
     * @return
     */
    List<Product> listProductByIds(@Param("productIds") List<Long> productIds);
}
