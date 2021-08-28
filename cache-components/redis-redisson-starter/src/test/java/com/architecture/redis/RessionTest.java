package com.architecture.redis;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class RessionTest {
    @Autowired
    private RedissonClient redisson;

    @Test
    public void testMap() throws InterruptedException {
        RMap<String, Object> rMap = redisson.getMap("test");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int j = 0; j < 10; j++)
            new Thread(() -> {
                for (int i = 1; i < 100000L; i++) {
                    rMap.put(UUID.randomUUID().toString(), i);
                }
                countDownLatch.countDown();
            }).start();
        countDownLatch.await();

    }

    @Test
    public void testSet() throws InterruptedException {
        RSet<Object> set = redisson.getSet("set");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int j = 0; j < 10; j++)
            new Thread(() -> {
                for (int i = 1; i < 100000L; i++) {
                    set.add(UUID.randomUUID());
                }
                countDownLatch.countDown();
            }).start();
        countDownLatch.await();
    }
}
