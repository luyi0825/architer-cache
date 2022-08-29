package io.github.architers.cache.annotation;

import java.lang.annotation.*;

/**
 * 多种缓存操作处理
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Caching {
    io.github.architers.cache.annotation.Cacheable[] cacheable() default {};

    io.github.architers.cache.annotation.DeleteCache[] delete() default {};

    io.github.architers.cache.annotation.PutCache[] put() default {};
}
