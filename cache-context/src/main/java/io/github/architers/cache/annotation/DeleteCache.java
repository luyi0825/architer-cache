package io.github.architers.annotation;


import io.github.architers.CacheMode;
import io.github.architers.lock.LockEnum;
import io.github.architers.lock.Locked;
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
     * 缓存值:此字段用于做批量操作（all表示删除cacheName对应的所有的缓存，其他根据对应的值删除）
     */
    String cacheValue() default "";

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

    /**
     * 缓存管理器类型
     *
     * @see Cacheable#cacheManager()
     */
    String cacheManager() default "";


}
