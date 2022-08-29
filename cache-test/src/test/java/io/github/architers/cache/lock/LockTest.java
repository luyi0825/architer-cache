package io.github.architers.cache.lock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class LockTest {
    @Autowired
    private CacheLockService cacheLockService;

    /**
     * 测试外部注解锁
     */
    @Test
    public void test1() throws InterruptedException {
        int count = 100;
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int j = 0; j < count; j++) {
            executorService.submit(() -> {
                try {
                    for (int i = 0; i <  100; i++) {
                        System.out.println(cacheLockService.test1(userName));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - startTime);

    }

    /**
     *
     */
    @Test
    public void callBack1() throws InterruptedException {
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    cacheLockService.test2(userName);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
