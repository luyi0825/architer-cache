package io.github.architers.cache.redis;


import io.github.architers.cache.Cache;
import org.springframework.util.Assert;

/**
 * @author luyi
 * redis缓存基类
 */
public abstract class BaseRedisCache implements Cache {
    /**
     * 缓存名称
     */
    protected final String cacheName;

    public BaseRedisCache(String cacheName) {
        Assert.notNull(cacheName, "缓存名称不能为空");
        this.cacheName = cacheName;
    }

    protected String getCacheKey(Object key) {
        return String.join(RedisConstants.SPLIT, cacheName, key.toString());
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }

}
