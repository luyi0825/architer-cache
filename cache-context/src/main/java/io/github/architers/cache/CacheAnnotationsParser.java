package io.github.architers.cache;


import io.github.architers.cache.annotation.*;
import io.github.architers.cache.lock.LockType;
import io.github.architers.cache.lock.Locked;
import io.github.architers.cache.operation.*;;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * @author luyi
 * 缓存注解解析,解析方法有哪些缓存操作的注解
 */
public class CacheAnnotationsParser {

    Map<AnnotatedElement, Collection<BaseCacheOperation>> operationCache = new HashMap<>(16);
    Map<AnnotatedElement, Locked> lockedCache = new HashMap<>(8);

    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(8);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cacheable.class);
        CACHE_OPERATION_ANNOTATIONS.add(Cacheables.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCaches.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCaches.class);
        CACHE_OPERATION_ANNOTATIONS.add(Caching.class);
    }

    private Class<? extends Annotation> lockedAnnotation = Locked.class;


    public boolean isCandidateClass(Class<?> targetClass) {
        return AnnotationUtils.isCandidateClass(targetClass, CACHE_OPERATION_ANNOTATIONS) || AnnotationUtils.isCandidateClass(targetClass, lockedAnnotation);
    }


    public Collection<BaseCacheOperation> parse(AnnotatedElement annotatedElement) {
        Collection<BaseCacheOperation> ops = operationCache.get(annotatedElement);
        if (ops != null) {
            return ops;
        }
        ops = parseCacheAnnotations(annotatedElement, false);
        if (ops != null && ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            Collection<BaseCacheOperation> localOps = parseCacheAnnotations(annotatedElement, true);
            if (localOps != null) {
                ops = localOps;
            }
        }
        if (!CollectionUtils.isEmpty(ops)) {
            operationCache.put(annotatedElement, ops);
        }

        return ops;
    }

    /**
     * 解析缓存注解
     *
     * @param annotatedElement 注解的元素
     * @param localOnly        是否只包含本方法
     * @return 缓存操作集合信息
     */
    @Nullable
    private Collection<BaseCacheOperation> parseCacheAnnotations(
            AnnotatedElement annotatedElement, boolean localOnly) {
        Collection<? extends Annotation> anns = (localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(annotatedElement, CACHE_OPERATION_ANNOTATIONS) :
                AnnotatedElementUtils.findAllMergedAnnotations(annotatedElement, CACHE_OPERATION_ANNOTATIONS));
        if (anns.isEmpty()) {
            return null;
        }
        final Collection<BaseCacheOperation> ops = new ArrayList<>(anns.size());
        anns.forEach(annotation -> {
            if (annotation instanceof Cacheable) {
                parseCacheableAnnotation((Cacheable) annotation, ops);
            } else if (annotation instanceof Cacheables) {
                parseCacheablesAnnotation((Cacheables) annotation, ops);
            } else if (annotation instanceof PutCache) {
                parsePutCacheAnnotation((PutCache) annotation, ops);
            } else if (annotation instanceof PutCaches) {
                parsePutCachesAnnotation((PutCaches) annotation, ops);
            } else if (annotation instanceof DeleteCache) {
                parseDeleteCacheAnnotation((DeleteCache) annotation, ops);
            } else if (annotation instanceof DeleteCaches) {
                parseDeletesCacheAnnotation((DeleteCaches) annotation, ops);
            } else if (annotation instanceof Caching) {
                parseCachingAnnotation((Caching) annotation, ops);
            }
        });
        return ops;
    }

    private void parseCacheablesAnnotation(Cacheables cacheables, Collection<BaseCacheOperation> ops) {
        Cacheable[] ables = cacheables.value();
        if (ables != null) {
            for (Cacheable cacheable : ables) {
                parseCacheableAnnotation(cacheable, ops);
            }
        }
    }

    private void parseDeletesCacheAnnotation(DeleteCaches deleteCaches, Collection<BaseCacheOperation> ops) {
        DeleteCache[] deletes = deleteCaches.value();
        if (deletes != null) {
            for (DeleteCache deleteCache : deletes) {
                parseDeleteCacheAnnotation(deleteCache, ops);
            }
        }
    }

    private void parsePutCachesAnnotation(PutCaches putCaches, Collection<BaseCacheOperation> ops) {
        PutCache[] puts = putCaches.value();
        if (puts != null) {
            for (PutCache put : puts) {
                parsePutCacheAnnotation(put, ops);
            }
        }
    }

    /**
     * 解析@Caching注解
     */
    private void parseCachingAnnotation(Caching caching, Collection<BaseCacheOperation> ops) {
        Cacheable[] cacheables = caching.cacheable();
        if (cacheables != null) {
            for (Cacheable cacheable : cacheables) {
                this.parseCacheableAnnotation(cacheable, ops);
            }
        }
        PutCache[] putCaches = caching.put();
        if (putCaches != null) {
            for (PutCache putCache : putCaches) {
                this.parsePutCacheAnnotation(putCache, ops);
            }
        }

        DeleteCache[] deleteCaches = caching.delete();
        if (deleteCaches != null) {
            for (DeleteCache deleteCache : deleteCaches) {
                this.parseDeleteCacheAnnotation(deleteCache, ops);
            }
        }
    }

    /**
     * 解析@PutCache注解
     */
    private void parsePutCacheAnnotation(PutCache cachePut,
                                         Collection<BaseCacheOperation> ops) {
        PutCacheOperation putCacheOperation = new PutCacheOperation();
        putCacheOperation.setKey(cachePut.key());
        putCacheOperation.setCacheName(cachePut.cacheName());
        putCacheOperation.setLocked(cachePut.locked());
        putCacheOperation.setAsync(cachePut.async());
        putCacheOperation.setExpireTime(cachePut.expireTime());
        putCacheOperation.setExpireTimeUnit(cachePut.expireTimeUnit());
        putCacheOperation.setRandomTime(cachePut.randomTime());
        putCacheOperation.setCondition(cachePut.condition());
        putCacheOperation.setUnless(cachePut.unless());
        putCacheOperation.setCacheValue(cachePut.cacheValue());
        putCacheOperation.setCacheMode(cachePut.cacheMode());
        ops.add(putCacheOperation);
    }

    /**
     * 解析删除缓存注解
     */
    private void parseDeleteCacheAnnotation(DeleteCache deleteCache,
                                            Collection<BaseCacheOperation> ops) {
        DeleteCacheOperation deleteCacheOperation = new DeleteCacheOperation();
        deleteCacheOperation.setCacheName(deleteCache.cacheName());
        deleteCacheOperation.setKey(deleteCache.key());
        deleteCacheOperation.setLocked(deleteCache.locked());
        deleteCacheOperation.setAsync(deleteCache.async());
        deleteCacheOperation.setCacheValue(deleteCache.cacheValue());
        deleteCacheOperation.setCacheMode(deleteCache.cacheMode());
        ops.add(deleteCacheOperation);
    }

    /**
     * 解析@Cacheable解析
     */
    private void parseCacheableAnnotation(Cacheable cacheable, Collection<BaseCacheOperation> ops) {
        CacheableOperation operation = new CacheableOperation();
        operation.setCacheName(cacheable.cacheName());
        operation.setExpireTime(cacheable.expireTime());
        operation.setExpireTimeUnit(cacheable.expireTimeUnit());
        operation.setRandomTime(cacheable.randomTime());
        operation.setAsync(cacheable.async());
        operation.setKey(cacheable.key());
        operation.setLocked(cacheable.locked());
        operation.setCondition(cacheable.condition());
        operation.setUnless(cacheable.unless());
        operation.setCacheMode(cacheable.cacheMode());
        //设置成最小，让cacheable最先执行
        operation.setOrder(-1);
        ops.add(operation);
    }

    /**
     * @param annotatedElement
     * @return
     */
    public Locked parseLocked(AnnotatedElement annotatedElement) {
        Locked locked = lockedCache.get(annotatedElement);
        if (locked != null) {
            return locked;
        }
        Set<Annotation> annotations = AnnotatedElementUtils.getAllMergedAnnotations(annotatedElement, Collections.singleton(Locked.class));
        for (Annotation annotation : annotations) {
            if (annotation instanceof Locked) {
                lockedCache.put(annotatedElement, (Locked) annotation);
                return (Locked) annotation;
            }
        }
        return null;
    }

    /**
     * @param annotatedElement
     * @return
     */
    public LockOperation parseLockOperation(AnnotatedElement annotatedElement) {
        Locked locked = parseLocked(annotatedElement);
        if (locked != null && !LockType.NONE.equals(locked.lockType())) {
            return new LockOperation();
        }
        return null;
    }
}
