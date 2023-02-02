package com.bjpowernode.service;

import com.bjpowernode.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
// 缓存配置
@CacheConfig(cacheNames = "USER")
public class UserServiceCache {

    @Cacheable
    public User getById(Long id) {
        System.out.println("从数据库获取数据");
        User user = new User();
        user.setId(id);
        user.setUsername("jack");
        return user;
    }

    // 更新数据的时候同步更新缓存
    @CachePut(key = "#user.id")
    public User saveOrUpdate(User user) {
        System.out.println("数据库更新id=" + user.getId() + "的用户");
        System.out.println("数据库:" + user);
        return user;
    }

    @CacheEvict(allEntries = true)
    // allEntries 清除所有缓存
    public void removeById(Long id) {
        System.out.println("从数据库删除id=" + id + "的用户");
    }
}
