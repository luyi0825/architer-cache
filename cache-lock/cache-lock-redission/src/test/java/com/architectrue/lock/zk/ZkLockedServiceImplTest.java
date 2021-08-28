package com.architectrue.lock.zk;


import com.architecture.context.cache.lock.LockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZkLockedServiceImplTest {

    @Autowired
    private LockService lockService;

    private static Long code = 0L;

    /**
     * 多线程不能保证可见性code，所以code的会小于<=200
     */
    @Test
    public void test() throws InterruptedException {
        int count = 200;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                code = code + 1;
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(code);
        Assertions.assertTrue(code < 200);
    }

    /**
     * 测试zk分布式锁: 锁can timeout
     */
    @Test
    public void testLockTimeOut() throws Exception {
        int count = 200;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                try {
                    addLockCode(30L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(code);
        assertEquals(count, (long) code);

        Lock lock = new ReentrantLock();
        lock.lock();
    }


    @Test
    public void testLock() throws Exception {
        int count = 200;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                try {
                    addLockCode(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(code);
        assertEquals(count, (long) code);

        Lock lock = new ReentrantLock();
        lock.lock();
    }


    /**
     * 减少库存
     */
    public void addLockCode(Long time) throws Exception {
        Lock lock;
        if (time != null && time > 0) {
            lock = lockService.tryFairLock("/code-lock/timeout", 30, TimeUnit.SECONDS);
        } else {
            lock = lockService.tryFairLock("/code-lock/no-timeout");
        }

        if (lock != null) {
            try {
                code = code + 1;
                System.out.println("add");
            } finally {
                System.out.println("释放锁");
                //  lock.unlock();
            }
        }
    }

}