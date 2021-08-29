package io.github.architers.cache.mapvalue;


import io.github.architers.cache.CacheMode;
import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.Cacheable;
import io.github.architers.cache.annotation.DeleteCache;

public interface MapValueService {
    String cacheName = "mapValue_user";

    @Cacheable(cacheName = "#root.target.cacheName", key = "#userName", expireTime = 2, cacheMode = CacheMode.MAP)
    UserInfo findByUserName(String userName);

    @DeleteCache(cacheName = "#root.target.cacheName", key = "#userName",cacheMode = CacheMode.MAP)
    void deleteByUserName(String userName);
}
