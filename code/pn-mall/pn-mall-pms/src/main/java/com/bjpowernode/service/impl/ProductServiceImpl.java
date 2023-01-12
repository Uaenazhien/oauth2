package com.bjpowernode.service.impl;

import com.bjpowernode.entity.Product;
import com.bjpowernode.mapper.ProductMapper;
import com.bjpowernode.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
