package io.github.architers.cache.put;

import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.PutCache;

public interface PutService {
    @PutCache(cacheName = "'onePut'", key = "#userInfo.username", cacheValue = "#userInfo")
    void onePut(UserInfo userInfo);

    @PutCache(cacheName = "'twoPut'", key = "#userInfo.username+'_1'", cacheValue = "#userInfo")
    @PutCache(cacheName = "'twoPut'", key = "#userInfo.username+'_2'", cacheValue = "#userInfo")
    void twoPut(UserInfo userInfo);

    @PutCache(cacheName = "'returnValue'", key = "#userInfo.username", cacheValue = "#result")
    UserInfo returnValue(UserInfo userInfo);

    @PutCache(cacheName = "'putCache_expireTime'", key = "#userInfo.username", cacheValue = "#userInfo.username",
            expireTime = 2)
    void expireTime(UserInfo userInfo);

    @PutCache(cacheName = "'putCache_randomTime'", key = "#userInfo.username", cacheValue = "#userInfo",
            expireTime = 2, randomTime = 2)
    void randomTime(UserInfo userInfo);

    @PutCache(cacheName = "'putCache_condition'", key = "#userInfo.username+'_1'", cacheValue = "#result", condition = "#result.username.startsWith('666')")
    UserInfo condition(UserInfo userInfo);

    @PutCache(cacheName = "'putCache_unless'", key = "#userInfo.username+'_1'", cacheValue = "#result", unless = "#result.username.startsWith('666')")
    UserInfo unless(UserInfo userInfo);
}
