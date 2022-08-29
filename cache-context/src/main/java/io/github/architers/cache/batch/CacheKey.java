package io.github.architers.cache.batch;

import io.github.architers.cache.annotation.Cacheables;

import java.lang.annotation.*;

/**
 * @author luyi
 * 缓存key的标识
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheKey {
}
