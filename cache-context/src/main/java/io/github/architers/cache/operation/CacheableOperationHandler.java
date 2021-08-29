package io.github.architers.cache.operation;


import io.github.architers.cache.Cache;
import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.model.NullValue;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import io.github.architers.cache.utils.CacheUtils;

import java.util.*;


/**
 * CacheableOperation 对应的处理类
 * 当缓存中没有值的时候，查询数据库，并将返回值放入缓存
 *
 * @author luyi
 * @version 1.0.0
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
            //设置返回值，防止重复调用
            methodReturnValueFunction.setValue(cacheValue);
        }
    }

    /**
     * 判断是否是空值
     *
     * @param value 缓存值
     * @return true表示为空值
     */
    private boolean isNullValue(Object value) {
        return value == null || value instanceof NullValue;
    }

    @Override
    public int getOrder() {
        return FIRST_ORDER;
    }
}
