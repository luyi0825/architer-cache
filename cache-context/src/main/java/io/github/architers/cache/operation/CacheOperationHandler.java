package io.github.architers.cache.operation;


import io.github.architers.cache.Cache;
import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.CacheMode;
import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.expression.ExpressionParser;
import io.github.architers.cache.lock.LockExecute;
import io.github.architers.cache.proxy.MethodReturnValueFunction;
import io.github.architers.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;


/**
 * @author luyi
 * 缓存operation处理基类
 * <li>对于实现类order排序,按照操作的频率排好序，增加程序效率：比如缓存读多，就把cacheable对应的处理器放最前边</li>
 */
public abstract class CacheOperationHandler implements Ordered {

    @Autowired(required = false)
    protected CacheManager cacheManager;

    @Autowired(required = false)
    protected LockExecute lockExecute;
    @Autowired(required = false)
    protected ExpressionParser expressionParser;


    public Object value(String valueExpression, ExpressionMetadata expressionMetadata) {
        return expressionParser.parserExpression(expressionMetadata, valueExpression);
    }

    /**
     * 选择缓存
     */
    public Cache chooseCache(BaseCacheOperation operation, String cacheName) {
        CacheMode cacheMode = operation.getCacheMode();
        if (CacheMode.SIMPLE.equals(cacheMode)) {
            return cacheManager.getSimpleCache(cacheName);
        }
        return cacheManager.getMapCache(cacheName);
    }

    /**
     * operation是否匹配
     *
     * @param operation operation对应的缓存操作
     * @return 是否匹配，如果true就对这个operation的进行缓存处理
     */
    public abstract boolean match(Operation operation);

    protected Object parseCacheKey(ExpressionMetadata expressionMetadata, String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("key is empty");
        }
        if (CacheConstants.BATCH_CACHE_KEY.equals(key)) {
            return key;
        }
        return expressionParser.parserExpression(expressionMetadata, key);
    }

    /**
     * 处理缓存operation
     *
     * @param operation
     * @param methodReturnValueFunction
     * @param expressionMetadata
     * @throws Throwable
     */
    public void handler(BaseCacheOperation operation, MethodReturnValueFunction methodReturnValueFunction, ExpressionMetadata expressionMetadata) throws Throwable {
        //这里初次过滤,没有包含result
        if (this.canHandler(operation, expressionMetadata, true)) {
            this.execute(operation, expressionMetadata, methodReturnValueFunction);
        }
    }


    /**
     * 能否处理
     *
     * @param excludeResult 是否排除#result
     * @return true标识condition满足，unless为false
     */
    protected boolean canHandler(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, boolean excludeResult) {
        String condition = operation.getCondition(), unless = operation.getUnless();
        if (!StringUtils.hasText(condition) && !StringUtils.hasText(unless)) {
            return true;
        }
        if (StringUtils.hasText(condition)) {
            if (excludeResult && isContainsResult(condition)) {
                return true;
            }
            Object isCondition = expressionParser.parserExpression(expressionMetadata, operation.getCondition());
            if (!(isCondition instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("condition[{0}]有误,必须为Boolean类型", operation.getCondition()));
            }
            return (boolean) isCondition;
        }

        if (StringUtils.hasText(unless)) {
            if (excludeResult && isContainsResult(unless)) {
                return true;
            }
            Object isUnless = expressionParser.parserExpression(expressionMetadata, unless);
            if (!(isUnless instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("unless[{0}]有误,必须为Boolean类型", unless));
            }
            return !(boolean) isUnless;
        }
        return true;
    }

    private boolean isContainsResult(String expression) {
        return expression.contains("#result");
    }

    /**
     * 执行缓存处理
     *
     * @param operation                 缓存操作对应的数据
     * @param expressionMetadata        表达式元数据
     * @param methodReturnValueFunction 返回值功能函数
     * @throws Throwable
     */
    protected abstract void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable;


    @Autowired
    public CacheOperationHandler setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        return this;
    }

    public CacheOperationHandler setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
        return this;
    }

}
