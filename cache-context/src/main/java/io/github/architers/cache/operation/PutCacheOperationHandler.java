package io.github.architers.cache.operation;


import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import io.github.architers.cache.utils.CacheUtils;

import java.util.Collection;


/**
 * 对应PutCacheOperation
 *
 * @author luyi
 */
public class PutCacheOperationHandler extends CacheOperationHandler {

    private static final int SECOND_ORDER = 2;

    @Override
    public boolean match(Operation operation) {
        return operation instanceof PutCacheOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        PutCacheOperation putCacheOperation = (PutCacheOperation) operation;
        Collection<String> cacheNames = getCacheNames(operation, expressionMetadata);
        long expireTime = CacheUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomTime());
        String cacheValue = putCacheOperation.getCacheValue();
        methodReturnValueFunction.proceed();
        Object value = expressionParser.parserExpression(expressionMetadata, cacheValue);
        if (this.canHandler(operation, expressionMetadata, false)) {
            lockExecute.execute(operation.getLocked(), expressionMetadata, () -> {
                String key = (String) expressionParser.parserExpression(expressionMetadata, operation.getKey());
                for (String cacheName : cacheNames) {
                    chooseCache(operation, cacheName).set(key, value, expireTime, putCacheOperation.getExpireTimeUnit());
                }
                return null;
            });
        }
    }

    @Override
    public int getOrder() {
        return SECOND_ORDER;
    }
}
