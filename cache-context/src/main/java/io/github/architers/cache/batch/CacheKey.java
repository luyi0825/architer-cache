package io.github.architers.batch;

import io.github.architers.annotation.Cacheables;

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
