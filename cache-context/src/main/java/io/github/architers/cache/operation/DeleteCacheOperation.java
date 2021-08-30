package io.github.architers.cache.operation;

/**
 * 删除：对应@DeleteOperation
 *
 * @author luyi
 */
public class DeleteCacheOperation extends BaseCacheOperation {
    /**
     * 缓存值
     */
    private String cacheValue;

    public String getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(String cacheValue) {
        this.cacheValue = cacheValue;
    }
}
