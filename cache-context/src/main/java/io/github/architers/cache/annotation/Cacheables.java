package io.github.architers.annotation;


import java.lang.annotation.*;


/**
 * @author luyi
 * @version 1.0.0
 * 支持多个Cacheable注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheables {
    Cacheable[] value();
}
