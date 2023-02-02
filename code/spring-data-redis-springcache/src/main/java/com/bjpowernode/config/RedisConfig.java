package com.bjpowernode.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 配置缓存管理器
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(redisCacheConfiguration())
                .build();
    }

    /**
     * 自定义缓存配置
     * @return
     */
    private RedisCacheConfiguration redisCacheConfiguration() {

        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheName -> cacheName + ":")
                //key的序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                //值的序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }


    // @Bean
    // public RedisTemplate<String,?> redisTemplate(RedisConnectionFactory factory) {
    //
    //     RedisTemplate<String,?> redisTemplate = new RedisTemplate<>();
    //     // 配置redis数据库的连接工厂
    //     redisTemplate.setConnectionFactory(factory);
    //     // 设置key序列化
    //     redisTemplate.setKeySerializer(RedisSerializer.string());
    //     redisTemplate.setHashKeySerializer(RedisSerializer.string());
    //     // 设置value序列化
    //     redisTemplate.setValueSerializer(RedisSerializer.json());
    //     redisTemplate.setHashValueSerializer(RedisSerializer.json());
    //     return redisTemplate;
    // }



}


// RedisConnectionFactory  key  value    key filed value