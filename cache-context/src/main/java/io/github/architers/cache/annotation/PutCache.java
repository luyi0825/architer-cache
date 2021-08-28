package io.github.architers.cache.annotation;


import io.github.architers.cache.CacheMode;
import io.github.architers.cache.lock.LockEnum;
import io.github.architers.cache.lock.Locked;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 向缓存中放数据
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(PutCaches.class)
public @interface PutCache {

    /**
     * @see Cacheable#cacheName()
     */
    String[] cacheName() default "";

    /**
     * 缓存值，支持EL表达式
     */
    String cacheValue() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * @see Cacheable#randomTime()
     */
    long randomTime() default 0L;


    /**
     * @see Cacheable#expireTime()
     */
    long expireTime() default -1;

    /**
     * @see Cacheable#expireTimeUnit()
     */
    TimeUnit expireTimeUnit() default TimeUnit.MINUTES;

    /**
     * @see Cacheable#locked()
     */
    Locked locked() default @Locked(lock = LockEnum.NONE, key = "");

    /**
     * @see Cacheable#async()
     */
    boolean async() default false;

    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";

    /**
     * 缓存模式
     */
    CacheMode cacheMode() default CacheMode.SIMPLE;
}
