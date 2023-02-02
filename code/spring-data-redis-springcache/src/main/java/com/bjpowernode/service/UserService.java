package com.bjpowernode.service;// package com.bjpowernode.service;
//
// import com.bjpowernode.entity.User;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Service;
//
// // @Service
// public class UserService {
//
//
//     @Autowired
//     private RedisTemplate<String, User> redisTemplate;
//
//     /**
//      * 1.先检查缓存中是否存在该数据
//      * 1.1 如果缓存中存在该数据,直接返回
//      * 1.2 如果缓存中不存在该数据,从数据库查询,将查询结果写入缓存,返回
//      *
//      * @param id
//      * @return
//      */
//     public User getById(Long id) {
//
//         User user = redisTemplate.opsForValue().get("USER:" + id);
//         // 如果缓存中存在该id的用户,直接返回
//         if (user != null) {
//             System.out.println("从缓存获取数据");
//             return user;
//         }
//
//         System.out.println("从数据库获取数据");
//         user = new User();
//         user.setId(id);
//         user.setUsername("jack");
//
//         // 将查询结果写入缓存
//         redisTemplate.opsForValue().set("USER:" + id, user);
//
//         return user;
//     }
//
//     // 更新数据的时候同步更新缓存
//     public void update(User user) {
//         System.out.println("数据库更新id=" + user.getId() + "的用户");
//         System.out.println("数据库:" + user);
//         // 将更新后的数据写入缓存
//         redisTemplate.opsForValue().set("USER:" + user.getId(),user);
//     }
//
//     public void removeById(Long id) {
//         System.out.println("从数据库删除id=" + id + "的用户");
//         // 删除对应缓存
//         redisTemplate.delete("USER:" + id);
//     }
// }
