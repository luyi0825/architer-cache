package io.github.architers.context.annotation;


import io.github.architers.context.CacheMode;
import io.github.architers.context.lock.LockEnum;
import io.github.architers.context.lock.Locked;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 删除缓存
 *
 * @author luyi
 * @date 2020/12/26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(DeleteCaches.class)
public @interface DeleteCache {

    /**
     * @see Cacheable#cacheName()
     */
    @AliasFor("value")
    String[] cacheName() default "";

    @AliasFor("cacheName")
    String[] value() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * 默认没有锁
     */
    Locked locked() default @Locked(lock = LockEnum.NONE, key = "");

    /**
     * @see Cacheable#async()
     */
    boolean async() default false;

    /**
     * true表示在方法直接删除缓存
     * false 表示在方法执行之后删除
     */
    boolean before() default true;

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
