package com.bjpowernode.config;

import com.bjpowernode.constant.RedisConstant;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
//启用缓存配置
@EnableCaching
public class RedisConfig {

    /**
     * 配置缓存管理器
     *
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                //设置key的分隔符
                .computePrefixWith(cacheName -> cacheName + ":")
                //key的序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                //值的序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                //不允许缓存空对象
                //.disableCachingNullValues();
                //设置所有key的过期时间
                .entryTtl(Duration.ofMinutes(20));

        //为指定的key设置过期时间
        Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        //为商品设置缓存过期时间
        cacheConfigurations.put(RedisConstant.KEY_PRODUCT,defaultConfig.entryTtl(Duration.ofMinutes(10)));
        //商品分类导航缓存时间永不过期
        cacheConfigurations.put(RedisConstant.KEY_INDEX_CATEGORY,defaultConfig.entryTtl(Duration.ZERO));


        return RedisCacheManager
                .builder(factory)
                //缓存配置
                .cacheDefaults(defaultConfig)
                //为指定key做缓存配置
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    /* *//**
     * 自定义缓存配置
     *
     * @return
     *//*
    private RedisCacheConfiguration redisCacheConfiguration() {

        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheName -> cacheName + ":")
                //key的序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                //值的序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }*/
}