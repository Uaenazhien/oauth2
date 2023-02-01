package com.bjpowernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductDTO;
import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.IndexContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjpowernode.param.IndexContentPageQueryParam;

import java.util.List;

/**
 * <p>
 * 首页内容 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
public interface IndexContentService extends IService<IndexContent> {
    /**
     * 分页查询
     * @param queryParam
     * @return
     */
    PageResult<IndexContent> pageQuery(IndexContentPageQueryParam queryParam);

    /**
     * 新增
     * @param indexContent
     * @return
     */
    boolean saveIndexContent(IndexContent indexContent);

    /**
     * 更新
     * @param indexContent
     * @return
     */
    boolean updateIndexContent(IndexContent indexContent);

    /**
     * 删除
     * @param indexContentId
     * @return
     */
    boolean removeIndexContentById(Long indexContentId);

    /**
     * 返回指定数量和类型的首页商品内容(首页调用)
     * @param contentType
     * @param num
     * @return
     */
     Result<List<ProductDTO>> getContentsForIndex(Integer contentType, Integer num);

}
