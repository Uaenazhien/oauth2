package com.bjpowernode.service;


import com.bjpowernode.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceCacheTest {

    @Autowired
    private UserServiceCache userServiceCache;

    @Test
    void getById() {
        User user = userServiceCache.getById(200L);
        System.out.println(user);

        System.out.println("------------------");

        User user1 = userServiceCache.getById(200L);
        System.out.println(user1);
    }

    @Test
    void saveOrUpate() {
        userServiceCache.saveOrUpdate(new User(1l,"user02"));
        userServiceCache.saveOrUpdate(new User(2l,"user02"));
    }

    @Test
    void removeById() {
        userServiceCache.removeById(1l);
    } 
}
