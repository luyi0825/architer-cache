package io.github.architers.mapvalue;


import io.github.architers.CacheMode;
import io.github.architers.UserInfo;
import io.github.architers.annotation.Cacheable;
import io.github.architers.annotation.DeleteCache;

public interface MapValueService {

    String cacheName="mapValue_user";

    @Cacheable(cacheName =MapValueService.cacheName, key = "#userName", expireTime = 2, cacheMode = CacheMode.MAP)
    UserInfo findByUserName(String userName);

    @DeleteCache(cacheName = MapValueService.cacheName, key = "#userName",cacheMode = CacheMode.MAP)
    void deleteByUserName(String userName);
}
