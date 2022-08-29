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
    io.github.architers.annotation.Cacheable[] cacheable() default {};

    io.github.architers.annotation.DeleteCache[] delete() default {};

    io.github.architers.annotation.PutCache[] put() default {};
}
