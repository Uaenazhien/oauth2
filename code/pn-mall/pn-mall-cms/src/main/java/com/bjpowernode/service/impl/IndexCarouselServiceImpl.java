package com.bjpowernode.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjpowernode.constant.RedisConstant;
import com.bjpowernode.dto.PageParam;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.entity.IndexCarousel;
import com.bjpowernode.mapper.IndexCarouselMapper;
import com.bjpowernode.service.IndexCarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.util.MinioUtil;
import com.bjpowernode.vo.IndexCarouselVO;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 首页商品轮播图 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
@Service
@CacheConfig(cacheNames = RedisConstant.KEY_INDEX_CAROUSEL)
public class IndexCarouselServiceImpl extends ServiceImpl<IndexCarouselMapper, IndexCarousel> implements IndexCarouselService {
    @Autowired
    private MinioUtil minioUtil;
    @Override
    public PageResult<IndexCarousel> pageQuery(PageParam queryParam) {
        //1.构造分页条件和结果集对象
        Page<IndexCarousel> page = new Page<>(queryParam.getCurrent(), queryParam.getSize());
        //2.执行分页查询
        super.page(page);
        //3.返回分页结果
        return new PageResult<>(page.getRecords(),page.getTotal());
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean saveIndexCarousel(IndexCarousel indexCarousel) {
        return super.save(indexCarousel);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateIndexCarousel(IndexCarousel indexCarousel) {
        return super.updateById(indexCarousel);
    }

    @Override
    @CacheEvict(allEntries = true)
    //清除所有轮播图的缓存
    public boolean removeIndexCarousel(Long indexCarouselId) {
        //获取图片路径
        IndexCarousel indexCarousel = super.getById(indexCarouselId);
        //1.删除数据
        boolean result = super.removeById(indexCarouselId);
        //2.删除图片
        String img = indexCarousel.getImg();
        if (StrUtil.isNotBlank(img)){
            String filename = img.substring(img.lastIndexOf("/") +1);
            minioUtil.removeFile(filename);
        }
        return result;
    }

    @Override
    @Cacheable //index:carousel:num--->List<IndexCarouselVo>(json)
    public List<IndexCarouselVO> getCarouselsForIndex(Integer num) {
        //1.构造条件和结果集对象
        Page<IndexCarousel> page = new Page<>(1,num);
        //2.构造排序条件
        LambdaQueryWrapper<IndexCarousel> queryWrapper = new LambdaQueryWrapper<>();
        //3.按照排序由小到大进行展示
        queryWrapper.orderByAsc(IndexCarousel::getSort);
        super.page(page,queryWrapper);
        //返回结果
        List<IndexCarouselVO> result = Lists.newArrayList();
        page.getRecords().forEach(indexCarousel -> {
            IndexCarouselVO indexCarouselVO = new IndexCarouselVO();
            BeanUtils.copyProperties(indexCarousel,indexCarouselVO);
            result.add(indexCarouselVO);
        });
        return result;
    }
}
