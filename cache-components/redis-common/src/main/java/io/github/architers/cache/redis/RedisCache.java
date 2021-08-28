package io.github.architers.cache.redis;



import io.github.architers.cache.Cache;
import org.springframework.util.Assert;

/**
 * @author luyi
 */
public abstract class RedisCache implements Cache {

    protected final String cacheName;

    public RedisCache(String cacheName) {
        Assert.notNull(cacheName, "缓存名称不能为空");
        this.cacheName = cacheName;
    }

    protected String getCacheKey(String key) {
        return String.join(RedisConstants.SPLIT, cacheName, key);
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }

}
