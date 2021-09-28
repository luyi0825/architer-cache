package io.github.architers.cache.batch;

import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.CacheMode;
import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.DeleteCache;
import io.github.architers.cache.annotation.PutCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BatchMapService {

    @PutCache(cacheName = "BatchMapService_batchMap", cacheMode = CacheMode.MAP, key = CacheConstants.BATCH_CACHE_KEY, expireTime = 30, cacheValue = "#userMap")
    public void batchPutMap(Map<String, UserInfo> userMap) {

    }

    @PutCache(cacheName = "BatchMapService_batchCollection", cacheMode = CacheMode.MAP, key = CacheConstants.BATCH_CACHE_KEY, expireTime = 30, cacheValue = "#userInfos")
    public void batchPutCollection(List<UserInfo> userInfos) {
    }

    @DeleteCache(cacheName = "BatchMapService_batchCollection", cacheMode = CacheMode.MAP, key = CacheConstants.BATCH_CACHE_KEY, cacheValue = "#userInfos")
    public void batchDeleteCollection(List<Object> userInfos) {
    }

    @DeleteCache(cacheName = "BatchMapService_batchCollection", cacheMode = CacheMode.MAP, key = CacheConstants.BATCH_CACHE_KEY, cacheValue = CacheConstants.CLEAR_ALL)
    public void clearAll() {
    }
}
