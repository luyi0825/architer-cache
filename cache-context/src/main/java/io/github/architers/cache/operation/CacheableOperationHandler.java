package io.github.architers.cache.operation;


import io.github.architers.cache.Cache;
import io.github.architers.cache.model.InvalidCache;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import io.github.architers.cache.utils.CacheUtils;
import com.architer.context.expression.ExpressionMetadata;

import java.util.*;


/**
 * CacheableOperation 对应的处理类
 *
 * @author luyi
 */
public class CacheableOperationHandler extends CacheOperationHandler {

    private static final int FIRST_ORDER = 1;

    @Override
    public boolean match(Operation operation) {
        return operation instanceof CacheableOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CacheableOperation cacheableOperation = (CacheableOperation) operation;
        Collection<String> cacheNames = getCacheNames(cacheableOperation, expressionMetadata);
        String key = Objects.requireNonNull(expressionParser.parserExpression(expressionMetadata, operation.getKey())).toString();
        Object cacheValue = null;
        for (String cacheName : cacheNames) {
            Cache cache = chooseCache(operation, cacheName);
            Object value = cache.get(key);
            if (!isNullValue(value)) {
                cacheValue = value;
                break;
            }
        }
        if (cacheValue == null) {
            lockExecute.execute(operation.getLocked(), expressionMetadata, () -> {
                long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomTime());
                Object returnValue = methodReturnValueFunction.proceed();
                for (String cacheName : cacheNames) {
                    Cache cache = chooseCache(operation, cacheName);
                    cache.set(key, returnValue, expireTime, ((CacheableOperation) operation).getExpireTimeUnit());
                }
                return null;
            });
        } else {
            methodReturnValueFunction.setValue(cacheValue);
        }
    }

    private boolean isNullValue(Object value) {
        return value == null || value instanceof InvalidCache;
    }

    @Override
    public int getOrder() {
        return FIRST_ORDER;
    }
}
