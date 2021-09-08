package io.github.architers.cache.lock;


import io.github.architers.cache.annotation.Cacheable;
import io.github.architers.cache.annotation.PutCache;

import java.util.concurrent.TimeUnit;

public interface CacheLockService {
    @Locked(lockName = "'CacheLockService_test1'", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.SECONDS)
        //@Cacheable(cacheName = "'test'",key = "#lockKey",expireTime = 2)
    void test1(String lockKey);

    @Locked(lockName = "'CacheLockService_test1'", key = "#lockKey", tryTime = 1, callBack = "callBack2", timeUnit = TimeUnit.SECONDS, failStrategy = FailStrategy.CAll_BACK)
    @PutCache(cacheName = "'test'",key = "#lockKey",expireTime = 2)
    void test2(String lockKey);


}
