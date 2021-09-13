package io.github.architers.cache.cacheable;


import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.Cacheable;
import io.github.architers.cache.lock.LockEnum;
import io.github.architers.cache.lock.Locked;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public interface CacheableService {
    @Cacheable(cacheName = "cacheableService_oneCacheable", key = "#userName")
    UserInfo oneCacheable(String userName);

    @Cacheable(cacheName = "'cacheableService_twoCacheable_key1'", key = "#userName")
    @Cacheable(cacheName = "'cacheableService_twoCacheable_key2'", key = "#userName")
    UserInfo twoCacheable(String userName);

    /**
     * 不过期
     */
    @Cacheable(cacheName = "'expireTime_1'", key = "#userName", expireTime = -1L)
    UserInfo expireTime_1(String userName);

    /**
     * 错误的过期时间
     */
    @Cacheable(cacheName = "'expireTime_2'", key = "#userName", expireTime = -3L)
    UserInfo expireTime_2(String userName);

    /**
     * 过期
     */
    @Cacheable(cacheName = "'expireTime_3'", key = "#userName", expireTime = 60)
    UserInfo expireTime_3(String userName);

    /**
     * 随机时间
     */
    @Cacheable(cacheName = "'randomTime'", key = "#userName", expireTime = 120, expireTimeUnit = TimeUnit.SECONDS, randomTime = 40)
    UserInfo randomTime(String userName);

    /**
     * userName长度大于10
     */
    @Cacheable(cacheName = "'condition'", key = "#userName", condition = "#userName.length>10")
    UserInfo condition(String userName);

    /**
     * unless的不缓存
     */
    @Cacheable(cacheName = "'unless'", key = "#userName", unless = "#userName.startsWith('unless')")
    UserInfo unless(String userName);

    /**
     * 测试并发，没有锁
     */
    @Cacheable(cacheName = "'toGather'", key = "#userName", expireTime = 2)
    UserInfo toGather(String userName);

    /**
     * 测试并发，加锁
     */
    @Cacheable(cacheName = "'testLockToGather'", key = "#userName", expireTime = 2,
            locked = @Locked(key = "'lock_testLockToGather'", lock = LockEnum.REDIS))
    UserInfo testLockToGather(String userName);
}
