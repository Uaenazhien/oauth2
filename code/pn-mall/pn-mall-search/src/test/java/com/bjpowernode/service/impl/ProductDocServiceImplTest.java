package com.bjpowernode.service.impl;

import com.bjpowernode.document.ProductDoc;
import com.bjpowernode.service.ProductDocService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductDocServiceImplTest {

    @Autowired
    private ProductDocService productDocService;


    @Test
    void saveOrUpdate() {

        ProductDoc product1 = new ProductDoc();
        product1.setId(101L);


        product1.setTitle("HUAWEI P150 原色双影像单元 搭载HarmonyOS 2 " +
                "万象双环设计 支持66W超级快充 8GB+256GB雪域白 华为手机");
        product1.setIntro("【华为官方直供，现货速发】下单赠华为原装66W充电器+碎屏险季卡");
        product1.setPrice(new BigDecimal("4558.0"));
        product1.setImg("https://mall.imcode.top/product/01.jpg");
        product1.setSales(80);

        product1.setCategoryIdLevel1(1L);
        product1.setCategoryIdLevel2(2L);
        product1.setCategoryIdLevel3(3L);
        product1.setCategoryNameLevel1("数码家电");
        product1.setCategoryNameLevel2("手机");
        product1.setCategoryNameLevel3("新品");
        productDocService.saveOrUpdate(product1);


        ProductDoc product2 = new ProductDoc();
        product2.setId(102L);

        product2.setTitle("小米13 Pro 开启预约！12月1日 xiaomi 13系列发布会 年度全能旗舰 小米手机");
        product2.setIntro("小米新品震撼来袭，敬请期待12月1日19:00新品发布会！具体价格和参数以发布会为主~");
        product2.setPrice(new BigDecimal("4299.0"));
        product2.setImg("https://mall.imcode.top/product/02.jpg");
        product2.setSales(100);

        product2.setCategoryIdLevel1(1L);
        product2.setCategoryIdLevel2(2L);
        product2.setCategoryIdLevel3(3L);
        product2.setCategoryNameLevel1("数码家电");
        product2.setCategoryNameLevel2("手机");
        product2.setCategoryNameLevel3("热销");

        productDocService.saveOrUpdate(product2);
    }
}