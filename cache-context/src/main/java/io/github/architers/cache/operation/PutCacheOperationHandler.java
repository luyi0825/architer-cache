package io.github.architers.cache.operation;


import io.github.architers.cache.Cache;
import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import io.github.architers.cache.utils.CacheUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;


/**
 * 对应PutCacheOperation的handler,先调用返回结果，后put.
 * <li>默认使用方法的返回值作为缓存，如果指定了使用了缓存值就用指定的缓存值</li>
 * <li>支持批量设置缓存</li>
 * <li>支持锁</li>
 *
 * @author luyi
 * @version 1.0.0
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
        Object value = methodReturnValueFunction.proceed();
        //默认为方法的返回值，当设置了返回值就用指定的返回值
        if (StringUtils.hasText(cacheValue)) {
            value = expressionParser.parserExpression(expressionMetadata, cacheValue);
        }
        if (this.canHandler(operation, expressionMetadata, false)) {
            Object finalValue = value;
            lockExecute.execute(operation.getLocked(), expressionMetadata, () -> {
                Object key = parseCacheKey(expressionMetadata, operation.getKey());
                for (String cacheName : cacheNames) {
                    Cache cache = chooseCache(operation, cacheName);
                    if (CacheConstants.BATCH_CACHE_KEY.equals(key)) {
                        cache.multiSet(finalValue, expireTime, putCacheOperation.getExpireTimeUnit());
                    } else {
                        cache.set(key, finalValue, expireTime, putCacheOperation.getExpireTimeUnit());
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
