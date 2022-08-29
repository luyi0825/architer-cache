package io.github.architers.proxy;


import io.github.architers.CacheAnnotationsParser;
import io.github.architers.operation.BaseCacheOperation;
import io.github.architers.operation.LockOperation;
import io.github.architers.operation.Operation;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author luyi
 * 注解缓存操作元
 */
public class AnnotationCacheOperationSource implements CacheOperationSource {

    /**
     * 缓存解析器
     */
    private final CacheAnnotationsParser cacheAnnotationsParser;

    public AnnotationCacheOperationSource(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return cacheAnnotationsParser.isCandidateClass(targetClass);
    }

    @Override
    public Collection<Operation> getCacheOperations(Method method, Class<?> targetClass) {
        List<Operation> operations = new ArrayList<>(2);
        Collection<BaseCacheOperation> cacheOperations = cacheAnnotationsParser.parse(method);
        if (!CollectionUtils.isEmpty(cacheOperations)) {
            operations.addAll(cacheOperations);
        }
        LockOperation lockOperation = cacheAnnotationsParser.parseLockOperation(method);
        if (lockOperation != null) {
            operations.add(lockOperation);
        }
        return operations;
    }
}
