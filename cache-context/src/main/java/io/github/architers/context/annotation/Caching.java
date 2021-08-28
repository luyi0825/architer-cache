package io.github.architers.context.annotation;

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
    Cacheable[] cacheable() default {};

    DeleteCache[] delete() default {};

    PutCache[] put() default {};
}
