package com.bjpowernode.repository;

import com.bjpowernode.document.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocRepository extends ElasticsearchRepository<ProductDoc, Long> {

}