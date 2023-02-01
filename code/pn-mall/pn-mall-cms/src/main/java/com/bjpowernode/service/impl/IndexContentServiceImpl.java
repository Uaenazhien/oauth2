package com.bjpowernode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjpowernode.Feign.FeignPmsService;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductDTO;
import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.IndexContent;
import com.bjpowernode.mapper.IndexContentMapper;
import com.bjpowernode.param.IndexContentPageQueryParam;
import com.bjpowernode.service.IndexContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页内容 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
@Service
public class IndexContentServiceImpl extends ServiceImpl<IndexContentMapper, IndexContent> implements IndexContentService {

    @Autowired
    private FeignPmsService feignPmsService;
    @Override
    public PageResult<IndexContent> pageQuery(IndexContentPageQueryParam queryParam) {
        //1.构造分页条件和结果集对象
        Page<IndexContent> page = new Page<>(queryParam.getCurrent(),queryParam.getSize());
        //2.构造查询条件
        LambdaQueryWrapper<IndexContent> queryWrapper = new LambdaQueryWrapper<>();
        //2.1 内容名称
        queryWrapper.like(StrUtil.isNotBlank(queryParam.getContentName()),IndexContent::getContentName
        ,queryParam.getContentName());
        //2.2内容分类
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getContentType()),IndexContent::getContentType,
                queryParam.getContentType());
        super.page(page,queryWrapper);
        //3.返回分页结果对象
        return new PageResult<>(page.getRecords(),page.getTotal());
    }

    @Override
    public boolean saveIndexContent(IndexContent indexContent) {
        //TODO 1.校验内容分类是否合法
        //TODO 2.检查商品id在商品表是否存在 跨服务远程调用
        //1.校验内容分类是否合法
        
        return super.save(indexContent);
    }

    @Override
    public boolean updateIndexContent(IndexContent indexContent) {
        return super.updateById(indexContent);
    }

    @Override
    public boolean removeIndexContentById(Long indexContentId) {
        return super.removeById(indexContentId);
    }

    @Override
    public  Result<List<ProductDTO>> getContentsForIndex(Integer contentType, Integer num) {
        //1.根据内容类型和数量获取商品id得集合
        Page<IndexContent> page = new Page<>(1,num);

        //2.构造查询条件
        LambdaQueryWrapper<IndexContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IndexContent::getContentType,contentType);
        //排序
        queryWrapper.orderByAsc(IndexContent::getSort);
        super.page(page,queryWrapper);
        //封装商品id
        List<Long> productIds = Lists.newArrayList();
        page.getRecords().forEach(indexContent -> productIds.add(indexContent.getProductId()));
        //3.获取满足条件的商品信息

        return feignPmsService.listProductByProductIds(productIds);
    }
}
