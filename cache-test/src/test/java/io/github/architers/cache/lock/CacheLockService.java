package io.github.architers.cache.lock;


import java.util.concurrent.TimeUnit;

public interface CacheLockService {
    @Locked(lockName = "'CacheLockService_test1'", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.SECONDS)
        //@Cacheable(cacheName = "'test'",key = "#lockKey",expireTime = 2)
    void test1(String lockKey);
}
