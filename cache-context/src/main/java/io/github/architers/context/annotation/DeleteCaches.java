package io.github.architers.context.annotation;

import java.lang.annotation.*;


/**
 * @author luyi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DeleteCaches {
    DeleteCache[] value();
}
