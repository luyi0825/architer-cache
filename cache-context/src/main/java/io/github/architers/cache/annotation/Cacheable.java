
package io.github.architers.annotation;


import io.github.architers.CacheMode;
import io.github.architers.lock.LockEnum;
import io.github.architers.lock.Locked;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * 功能：缓存数据，缓存中有的时候，就从缓存中取值，没有就将返回结果放入缓存中并返回
 * ------------------------------------缓存的中key规则--------------------------
 * <br/>
 * 1.所有的时间单位都是秒
 * 2.缓存的中的key的取值为：
 * <li> a.如果cacheName为空就为key的取值</li>
 * <li>b.如果cacheName不为空，就为cacheName+缓存分隔符+key的取值</li>
 * -----------------------------------取默认配置值---------------------------
 * 当值为CacheConstants.DEFAULT_CONFIG_TIME的时候，会根据默认的配置取值，用户可以配置全局值
 * -----------------------------------缓存过期时间-------------------------------
 * <li>缓存过期时间：expireTime加上randomTime范围内随机生成的时间</li>
 *
 * @author luyi
 * @version 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(Cacheables.class)
public @interface Cacheable {

    /**
     * 缓存名称(不支持EL表达式)
     */
    String[] cacheName() default "";

    /**
     * 缓存key,支持SpEL
     */
    String key();

    /**
     * 缓存随机时间,时间单位跟expireTime一致
     * ps:主要用户解决缓存雪崩，同一时刻大量缓存数据失效，大量请求到达数据库
     */
    long randomTime() default 0;

    /**
     * 缓存失效时间
     */
    long expireTime() default -1;

    /**
     * 过期时间单位
     */
    TimeUnit expireTimeUnit() default TimeUnit.MINUTES;

    /**
     * 默认没有锁
     */
    Locked locked() default @Locked(lock = LockEnum.NONE, key = "");

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * 条件满足的时候，进行缓存操作
     */
    String condition() default "";

    /**
     * 条件满足的时候，不进行缓存操作
     */
    String unless() default "";

    /**
     * 缓存模式
     */
    CacheMode cacheMode() default CacheMode.SIMPLE;

    /**
     * 缓存管理器类型，对应spring容器中bean的名称
     */
    String cacheManager() default "";

}
