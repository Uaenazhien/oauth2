package com.bjpowernode.service;

import com.bjpowernode.document.ProductDoc;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.param.ProductSearchParam;

public interface ProductDocService {

    /**
     * 查询
     * @param param
     * @return
     */
    PageResult<ProductDoc> search(ProductSearchParam param);

    /**
     * 新增或更新文档
     * @param productDoc
     */
    void saveOrUpdate(ProductDoc productDoc);

    /**
     * 根据文档id删除文档
     * @param id
     */
    void deleteById(Long id);
}