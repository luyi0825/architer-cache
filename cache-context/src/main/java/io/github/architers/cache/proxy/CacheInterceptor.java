package io.github.architers.cache.proxy;


import io.github.architers.cache.CacheAnnotationsParser;
import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.expression.ExpressionEvaluationContext;
import io.github.architers.cache.expression.ExpressionMetadata;
import io.github.architers.cache.expression.ExpressionParser;
import io.github.architers.cache.lock.LockExecute;
import io.github.architers.cache.lock.Locked;
import io.github.architers.cache.model.NullValue;
import io.github.architers.cache.operation.BaseCacheOperation;
import io.github.architers.cache.operation.CacheOperationHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存拦截器：解析缓存、缓存锁相关的注解，进行相关的处理
 */
public class CacheInterceptor implements MethodInterceptor {

    private CacheAnnotationsParser cacheAnnotationsParser;

    private List<CacheOperationHandler> cacheOperationHandlers;

    private LockExecute lockExecute;

    @Override
    @Nullable
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        /*
         *构建表达式的元数据:expressionMetadata
         *1.由于对于同一个线程的ExpressionEvaluationContext一样，可能存在多个注解，再次构建，减少对象的创建
         *2.方便不同注解拓展变量值传递
         */
        ExpressionMetadata expressionMetadata = this.buildExpressionMeta(invocation);
        Locked locked = cacheAnnotationsParser.parseLocked(invocation.getMethod());
        return lockExecute.execute(locked, expressionMetadata, () -> {
            Collection<BaseCacheOperation> baseCacheOperations = cacheAnnotationsParser.parse(invocation.getMethod());
            if (!CollectionUtils.isEmpty(baseCacheOperations)) {
                if (baseCacheOperations.size() > 1) {
                    //当多余一个注解的时候，排序，让cacheable操作在最前边
                    baseCacheOperations = baseCacheOperations.stream().sorted(Comparator.comparing(BaseCacheOperation::getOrder)).collect(Collectors.toList());
                }
                Object returnValue = execute(invocation, baseCacheOperations, expressionMetadata);
                //已经调用了方法，缓存中放的空值
                if (returnValue instanceof NullValue) {
                    return null;
                }
                //获取到返回值
                if (returnValue != null) {
                    return returnValue;
                }
                //没有调用过方法，调用一次
                return invocation.proceed();
            }
            return invocation.proceed();
        });

    }

    /**
     * 执行拦截的操作
     */
    private Object execute(MethodInvocation invocation, Collection<BaseCacheOperation> operations, ExpressionMetadata expressionMetadata) throws Throwable {
        //返回值构建，也方便多个注解的时候，重复调用方法
        AtomicReference<Object> returnValue = new AtomicReference<>();
        MethodReturnValueFunction methodReturnValueFunction = new MethodReturnValueFunction() {
            @Override
            public Object proceed() throws Throwable {
                synchronized (this) {
                    if (returnValue.get() == null) {
                        Object value = invocation.proceed();
                        if (value == null) {
                            value = NullValue.INVALID_CACHE;
                        }
                        setValue(value);
                    }
                    return returnValue.get();
                }
            }

            @Override
            public void setValue(Object value) {
                synchronized (this) {
                    if (value != null && !(value instanceof NullValue)) {
                        expressionMetadata.getEvaluationContext().setVariable("result", value);
                    }
                    if (value != null && returnValue.get() == null) {
                        returnValue.set(value);
                    }
                }
            }
        };
        for (BaseCacheOperation operation : operations) {
            for (CacheOperationHandler cacheOperationHandler : cacheOperationHandlers) {
                if (cacheOperationHandler.match(operation)) {
                    cacheOperationHandler.handler(operation, methodReturnValueFunction, expressionMetadata);
                    break;
                }
            }
        }
        return returnValue.get();
    }

    /**
     * 构建缓存的表达式元数据
     *
     * @param invocation 方法代理的信息
     * @return 表达式元数据
     */
    private ExpressionMetadata buildExpressionMeta(MethodInvocation invocation) {
        ExpressionMetadata expressionMetadata = new ExpressionMetadata(Objects.requireNonNull(invocation.getThis()), invocation.getMethod(), invocation.getArguments());
        ExpressionEvaluationContext expressionEvaluationContext = ExpressionParser.createEvaluationContext(expressionMetadata);
        expressionMetadata.setEvaluationContext(expressionEvaluationContext);
        return expressionMetadata;
    }


    public void setCacheAnnotationsParser(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }

    public CacheAnnotationsParser getCacheAnnotationsParser() {
        return cacheAnnotationsParser;
    }

    public List<CacheOperationHandler> getCacheOperationHandlers() {
        return cacheOperationHandlers;
    }

    public void setCacheOperationHandlers(List<CacheOperationHandler> cacheOperationHandlers) {
        this.cacheOperationHandlers = cacheOperationHandlers;
    }

    @Autowired(required = false)
    public void setLockExecute(LockExecute lockExecute) {
        this.lockExecute = lockExecute;
    }
}
