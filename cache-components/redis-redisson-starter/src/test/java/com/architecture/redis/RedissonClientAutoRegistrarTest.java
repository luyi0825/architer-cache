package com.architecture.redis;

import org.junit.jupiter.api.Test;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class RedissonClientAutoRegistrarTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() {
        User user = new User();
        user.setName("test");
        //redissonClient.reactive().
        redissonClient.getDeque("1").add(user);
        redisTemplate.delete(1);
    }
}