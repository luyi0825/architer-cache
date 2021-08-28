package io.github.architers.context.annotation;


import java.lang.annotation.*;


/**
 * @author luyi
 * 支持多个Cacheable注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheables {
    Cacheable[] value();
}
