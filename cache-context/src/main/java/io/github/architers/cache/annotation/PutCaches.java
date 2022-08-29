package io.github.architers.cache.annotation;


import java.lang.annotation.*;

/**
 * 向缓存中放数据
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PutCaches {

    PutCache[] value();
}
