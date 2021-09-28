package io.github.architers.cache.mapvalue;


import io.github.architers.cache.CacheMode;
import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.Cacheable;
import io.github.architers.cache.annotation.DeleteCache;

public interface MapValueService {

    @Cacheable(cacheName = "mapValue_user", key = "#userName", expireTime = 2, cacheMode = CacheMode.MAP)
    UserInfo findByUserName(String userName);

    @DeleteCache(cacheName = "mapValue_user", key = "#userName",cacheMode = CacheMode.MAP)
    void deleteByUserName(String userName);
}
