package io.github.architers.cache.operation;


import io.github.architers.cache.Cache;
import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.proxy.MethodReturnValueFunction;

import java.util.Collection;


/**
 * 对应DeleteCacheOperation
 * 默认：先删,后操作:防止redis出错了，造成数据不一致问题
 * <li>
 * 比如更新的时候:我们删除数据，如果delete操作在后，redis突然故障导致删除数据失败，导致我们从缓存取的值就有问题。
 * 当然如果redis故障异常，数据库开启了事物的情况下不会出现这个问题
 *
 * @author luyi
 */
public class DeleteCacheOperationHandler extends CacheOperationHandler {

    private static final int END_ORDER = 3;

    @Override
    public boolean match(Operation operation) {
        return operation instanceof DeleteCacheOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        methodReturnValueFunction.proceed();
        if (this.canHandler(operation, expressionMetadata, false)) {
            this.doDelete((DeleteCacheOperation) operation, expressionMetadata);
        }
    }

    /**
     * 执行删除操作
     *
     * @param operation          删除操作的信息
     * @param expressionMetadata 表达式元数据
     * @throws Throwable 锁执行的业务抛出的异常
     */
    private void doDelete(DeleteCacheOperation operation, ExpressionMetadata expressionMetadata) throws Throwable {
        lockExecute.execute(operation.getLocked(), expressionMetadata, () -> {
            Collection<String> cacheNames = getCacheNames(operation, expressionMetadata);
            String cacheValue = operation.getCacheValue();
            for (String cacheName : cacheNames) {
                Cache cache = chooseCache(operation, cacheName);
                if (CacheConstants.BATCH_CACHE_KEY.equals(operation.getKey())) {
                    Object value = this.expressionParser.parserExpression(expressionMetadata, cacheValue);
                    cache.multiDelete(value);
                } else {
                    Object cacheKey = super.parseCacheKey(expressionMetadata, operation.getKey());
                    cache.delete(cacheKey);
                }
            }
            return null;
        });
    }

    @Override
    public int getOrder() {
        return END_ORDER;
    }
}
