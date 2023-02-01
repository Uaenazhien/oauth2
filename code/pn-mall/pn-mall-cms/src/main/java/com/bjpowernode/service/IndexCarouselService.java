package com.bjpowernode.service;

import com.bjpowernode.dto.PageParam;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.entity.IndexCarousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjpowernode.vo.IndexCarouselVO;

import java.util.List;

/**
 * <p>
 * 首页商品轮播图 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
public interface IndexCarouselService extends IService<IndexCarousel> {
    /**
     * 分页查询
     * @param queryParam
     * @return
     */
    PageResult<IndexCarousel>  pageQuery(PageParam queryParam);

    /**
     * 新增首页轮播图
     * @param indexCarousel
     * @return
     */
    boolean saveIndexCarousel(IndexCarousel indexCarousel);

    /**
     * 更新首页轮播图
     * @param indexCarousel
     * @return
     */
    boolean updateIndexCarousel(IndexCarousel indexCarousel);

    /**
     * 删除首页轮播图
     * @param indexCarouselId
     * @return
     */
    boolean removeIndexCarousel(Long indexCarouselId);


    /**
     * 返回固定数量的轮播图对象（首页使用） 并且按照sort字段排序
     * @param num
     * @return
     */
    List<IndexCarouselVO> getCarouselsForIndex(Integer num);

}
