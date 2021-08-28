package com.architecture.redis;


import com.architecture.redis.support.cache.RedisValueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisValueService测试
 *
 * @author luyi
 */
@SpringBootTest
class ValueRedisCacheServiceImplTest {

    @Autowired
    private RedisValueService redisValueService;

    private String prefix = this.getClass().getName();

    @Test
    void set() {
        String set1 = UUID.randomUUID().toString(), set2 = UUID.randomUUID().toString(), value = "32";
        redisValueService.set(set1, value);
        Assertions.assertEquals(value, redisValueService.get(set1));
        redisValueService.set(set2, new User().setName(value));
        User user = (User) redisValueService.get(set2);
        Assertions.assertEquals(value, user.getName());
    }

    @Test
    void testSet() {
        String key = "testSet" + RedisConstants.SPLIT + UUID.randomUUID();
        User user = new User().setName("testSet");
        redisValueService.set(key, user, 2, TimeUnit.MINUTES);
        Assertions.assertEquals(2, redisValueService.getExpireTime(key, TimeUnit.MINUTES));
    }

    @Test
    void setIfAbsent() {
        String uuid = prefix + "_setIfAbsent" + RedisConstants.SPLIT + UUID.randomUUID().toString();
        boolean bool = redisValueService.setIfAbsent(uuid, 1);
        Assertions.assertTrue(bool);
        bool = redisValueService.setIfAbsent(uuid, 1);
        Assertions.assertFalse(bool);
    }

    @Test
    void testSetIfAbsent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = redisValueService.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
        Assertions.assertTrue(bool);
        bool = redisValueService.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
        Assertions.assertFalse(bool);
    }

    @Test
    void setIfPresent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = redisValueService.setIfPresent(uuid, 1);
        Assertions.assertFalse(bool);
        bool = redisValueService.setIfAbsent(uuid, 1);
        Assertions.assertTrue(bool);
    }

    @Test
    void testSetIfPresent1() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = redisValueService.setIfPresent(uuid, 1, 2, TimeUnit.MINUTES);
        Assertions.assertFalse(bool);
        bool = redisValueService.setIfAbsent(uuid, 1, 2, TimeUnit.MINUTES);
        Assertions.assertTrue(bool);
    }

    @Test
    void getAndSet() {
        String uuid = UUID.randomUUID().toString();
        Object value = redisValueService.getAndSet(uuid, "getAndSet");
        Assertions.assertNull(value);
        value = redisValueService.getAndSet(uuid, "23");
        Assertions.assertEquals("getAndSet", value);
    }

    @Test
    void get() {
        String uuid = UUID.randomUUID().toString();
        redisValueService.set(uuid, new User().setName(uuid));
        User user = (User) redisValueService.get(uuid);
        Assertions.assertEquals(uuid, user.getName());
    }

    @Test
    void testGet() {
        String uuid = UUID.randomUUID().toString();
        redisValueService.set(uuid, new User().setName(uuid));
        User user = redisValueService.get(uuid, User.class);
        Assertions.assertEquals(uuid, user.getName());
    }

    @Test
    void delete() {
        String uuid = UUID.randomUUID().toString();
        redisValueService.set(uuid, new User().setName(uuid));
        redisValueService.delete(uuid);
    }


}