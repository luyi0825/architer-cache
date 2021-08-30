package io.github.architers.cache.operation;


import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import io.github.architers.cache.utils.CacheUtils;

import java.util.Collection;


/**
 * 对应PutCacheOperation
 * 先调用返回结果，后put
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
        //解析缓存值
        Object value = expressionParser.parserExpression(expressionMetadata, cacheValue);
        if (this.canHandler(operation, expressionMetadata, false)) {
            lockExecute.execute(operation.getLocked(), expressionMetadata, () -> {
                Object key = parseCacheKey(expressionMetadata, operation.getKey());
                for (String cacheName : cacheNames) {
                    if (CacheConstants.NEVER_EXPIRE == expireTime) {
                        //永不过期
                        chooseCache(operation, cacheName).set(key, value);
                    } else {
                        //有过期时间
                        chooseCache(operation, cacheName).set(key, value, expireTime, putCacheOperation.getExpireTimeUnit());
                    }
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
