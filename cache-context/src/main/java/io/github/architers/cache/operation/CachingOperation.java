package io.github.architers.cache.operation;


/**
 * @author luyi
 * 多个缓存注解操作
 */
public class CachingOperation extends BaseCacheOperation {
    private BaseCacheOperation[] operations;

    public BaseCacheOperation[] getOperations() {
        return operations;
    }

    public void setOperations(BaseCacheOperation[] operations) {
        this.operations = operations;
    }
}
