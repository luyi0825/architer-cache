package io.github.architers.cache.exception;

/**
 * @author luyi
 * 缓存注解不合法
 */
public class CacheAnnotationIllegalException extends RuntimeException {

    public CacheAnnotationIllegalException(String message) {
        super(message);
    }
}
