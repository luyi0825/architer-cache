package io.github.architers.cache.operation;


import io.github.architers.cache.CacheMode;
import io.github.architers.cache.annotation.Cacheable;
import io.github.architers.cache.annotation.PutCache;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * @see PutCache
 */
public class PutCacheOperation extends BaseCacheOperation {

    /**
     * @see Cacheable#randomTime()
     */
    private long randomTime;
    /**
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * @see Cacheable#expireTimeUnit()
     */
    private TimeUnit expireTimeUnit;
    /**
     * 缓存值
     */
    private String cacheValue;

    /**
     * 缓存模式
     */
    private CacheMode cacheMode;

    public long getRandomTime() {
        return randomTime;
    }

    public void setRandomTime(long randomTime) {
        this.randomTime = randomTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public TimeUnit getExpireTimeUnit() {
        return expireTimeUnit;
    }

    public void setExpireTimeUnit(TimeUnit expireTimeUnit) {
        this.expireTimeUnit = expireTimeUnit;
    }

    public String getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(String cacheValue) {
        this.cacheValue = cacheValue;
    }

    @Override
    public CacheMode getCacheMode() {
        return cacheMode;
    }

    @Override
    public void setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
    }
}
