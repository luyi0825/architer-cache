package io.github.architers.cache.operation;


import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import org.springframework.util.StringUtils;


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
        DeleteCacheOperation deleteCacheOperation = (DeleteCacheOperation) operation;
        if (this.canHandler(operation, expressionMetadata, false)) {
            lockExecute.execute(operation.getLocked(), expressionMetadata, () -> {
                Collection<String> cacheNames = getCacheNames(operation, expressionMetadata);
                String cacheValue = deleteCacheOperation.getCacheValue();
                if ("all".equals(cacheValue)) {
                    for (String cacheName : cacheNames) {
                        chooseCache(operation, cacheName).clearAll();
                    }
                } else if (StringUtils.hasText(cacheValue)) {
                    Object value = this.expressionParser.parserExpression(expressionMetadata, cacheValue);
                } else {
                    for (String cacheName : cacheNames) {
                        Object key = super.parseCacheKey(expressionMetadata, operation.getKey());
                        chooseCache(operation, cacheName).delete(key);
                    }
                }
                return null;
            });
        }
    }

    @Override
    public int getOrder() {
        return END_ORDER;
    }
}
