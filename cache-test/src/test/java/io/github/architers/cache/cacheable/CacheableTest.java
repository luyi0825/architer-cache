package io.github.architers.cacheable;


import io.github.architers.CacheManager;
import io.github.architers.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luyi
 * 用于测试@Cacheable注解
 * @version 1.0.0
 */
@SpringBootTest
public class CacheableTest {
    @Autowired
    private CacheableService cacheableService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedissonClient client;

    /**
     * 测试一个注解
     */
    @Test
    public void testOneCacheable() {
        String userId = UUID.randomUUID().toString();
        long count = 10000;
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = cacheableService.oneCacheable(userId);
            Assertions.assertNotNull(userInfo);
        }
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
        for (int i = 0; i < count; i++) {
            cacheManager.getSimpleCache("test").get("1");
        }
        long time3 = System.currentTimeMillis();
        System.out.println(time3 - time2);

        for (int i = 0; i < count; i++) {
            redisTemplate.opsForValue().get("test");
        }
        long time4 = System.currentTimeMillis();
        System.out.println(time4 - time3);
        for (int i = 0; i < count; i++) {
            client.getAtomicLong("test2").addAndGet(1);
        }
        long time5 = System.currentTimeMillis();
        System.out.println(time5 - time4);
    }

    /**
     * 测试两个注解
     * <li>需要测试是否造成多次查询</li>
     */
    @Test
    public void testTwoCacheable() {
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < 5; i++) {
            //删除一个，再获取
            cacheManager.getSimpleCache("cacheableService_twoCacheable_key2").delete(userId);
            UserInfo userInfo = cacheableService.twoCacheable(userId);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试expireTime
     */
    @Test
    public void testExpireTime() {
        int count = 5;
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = cacheableService.expireTime_1(userId);
            Assertions.assertNotNull(userInfo);
        }

        userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            String finalUserId = userId;
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                UserInfo userInfo = cacheableService.expireTime_2(finalUserId);
                Assertions.assertNull(userInfo);
            });
        }
        userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = cacheableService.expireTime_3(userId);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试随机时间
     */
    @Test
    public void testRandomTime() {
        int count = 100;
        for (int i = 0; i < count; i++) {
            String userName = UUID.randomUUID().toString();
            cacheableService.randomTime(userName);
            UserInfo userInfo = cacheableService.randomTime(userName);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试条件
     */
    @Test
    public void testCondition() {
        //缓存，查询1次
        StringBuilder stringBuilder = new StringBuilder("");
        int count = 11;
        for (int i = 0; i < count; i++) {
            stringBuilder.append("1");
        }
        String userName = stringBuilder.toString();
        cacheableService.condition(userName);
        UserInfo userInfo = cacheableService.condition(userName);
        Assertions.assertNotNull(userInfo);
        //不缓存：查询db两次
        userName = stringBuilder.substring(0, count - 2);
        cacheableService.condition(userName);
        userInfo = cacheableService.condition(userName);
        Assertions.assertNotNull(userInfo);
    }

    /**
     * 测试unless
     */
    @Test
    public void testUnless() {
        //缓存，查询1次
        String userName = "no_unless";
        cacheableService.unless(userName);
        UserInfo userInfo = cacheableService.unless(userName);
        Assertions.assertNotNull(userInfo);
        //不缓存：查询db两次
        userName = "unless";
        cacheableService.unless(userName);
        userInfo = cacheableService.unless(userName);
        Assertions.assertNotNull(userInfo);
    }

    /**
     * 测试并发
     */
    @Test
    public void testToGather() throws InterruptedException {
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                cacheableService.toGather(userName);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * 测试并发
     */
    @Test
    public void testLockToGather() throws InterruptedException {
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    UserInfo userInfo = cacheableService.testLockToGather(userName);
                    Assertions.assertNotNull(userInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

    }


}
