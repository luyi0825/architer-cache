package io.github.architers.cache.batch;

import java.lang.annotation.*;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存值的标识
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheValue {
}
