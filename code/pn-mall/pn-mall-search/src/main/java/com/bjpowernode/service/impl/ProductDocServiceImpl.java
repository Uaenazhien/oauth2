package com.bjpowernode.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.bjpowernode.document.ProductDoc;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.param.ProductSearchParam;
import com.bjpowernode.repository.ProductDocRepository;
import com.bjpowernode.service.ProductDocService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;

import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDocServiceImpl implements ProductDocService{
    @Autowired
    private ProductDocRepository repository;
    @Autowired
    private ElasticsearchRestTemplate template;
    @Override
    public PageResult<ProductDoc> search(ProductSearchParam param) {
        //获取搜索的关键字
        String keyword = param.getKeyword();
        //为输入搜索关键字 直接返回
        if (StrUtil.isBlank(keyword)){
            return new PageResult<>();
        }

        //单查询条件查询 查询条件支持分词
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.
                must(QueryBuilders.matchQuery("keyword",param.getKeyword()));
        //价格区间过滤 都不能为空 动态
        BigDecimal lowPrice = param.getLowPrice();
        BigDecimal higthPrice = param.getHigthPrice();
        if (ObjUtil.isAllNotEmpty(lowPrice,higthPrice)){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(lowPrice).lte(higthPrice));
        }
        //构造query对象
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();/*
        //分页和排序
        query.setPageable(PageRequest.of(param.getCurrent()-1,param.getSize()));*/
        //分页
        Integer current = param.getCurrent();
        Integer size = param.getSize();
        if (ObjUtil.isAllNotEmpty(current,size)){
            query.setPageable(PageRequest.of(current-1,size));
        }else {
            query.setPageable(PageRequest.of(0,20));
        }

        //排序字段
        String orderBy = param.getOrderBy();
        //排序方向  asc desc
        String direction = param.getDirection();

        if (ObjUtil.isAllNotEmpty(orderBy,direction)){
            if (direction.equalsIgnoreCase("asc")){
                query.addSort(Sort.by(Sort.Direction.ASC,orderBy));
            }
            if (direction.equalsIgnoreCase("desc")){
                query.addSort(Sort.by(Sort.Direction.DESC, orderBy));
            }
        }

        List<SearchHit<ProductDoc>> searchHits = template.search(query, ProductDoc.class).getSearchHits();
        List<ProductDoc> records = new ArrayList<>();
        searchHits.forEach(item -> records.add(item.getContent()));
        //满足条件的总记录数
        long total = template.search(query, ProductDoc.class).getTotalHits();

        return new PageResult<>(records,total);
    }

    @Override
    public void saveOrUpdate(ProductDoc product) {
        repository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}