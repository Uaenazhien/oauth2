package com.bjpowernode.constant;

public interface RabbitMQConstant {
    // 商品的交换机
    String EXCHANGE_PRODUCT = "pms.product";

    // 新增和更新商品 队列
    String QUEUE_PRODUCT_SAVE = "pms.product.save";

    // 删除商品的 队列
    String QUEUE_PRODUCT_DEL = "pms.product.del";

    // 新增和更新商品 key
    String ROUTING_KEY_PRODUCT_SAVE = "product.save";

    // 删除商品的 key
    String ROUTING_KEY_PRODUCT_DEL = "product.del";
}