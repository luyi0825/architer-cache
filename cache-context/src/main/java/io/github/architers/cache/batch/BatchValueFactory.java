package io.github.architers.cache.batch;

import io.github.architers.cache.exception.CacheAnnotationIllegalException;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批量值的解析
 *
 * @author luyi
 * @version 1.0.0
 */
public class BatchValueFactory {
    /**
     * 表示值为当前对象
     */
    private static final String THIS_VALUE = "this";
    /**
     * 缓存类的缓存字段信息
     */
    Map<String, CacheField> fieldCaches = new ConcurrentHashMap<>();


    /**
     * 通过反射得到对象的缓存字段信息
     *
     * @param object 对应的缓存对象
     * @return 缓存字段信息
     */
    public CacheField getObjectCacheField(Object object) {
        CacheField cacheField = new CacheField();
        Class<?> clazz = object.getClass();
        //是否解析到key和value（减少遍历）
        boolean parsedKey = false, parsedValue = false;
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CacheValue) {
                cacheField.setValue(THIS_VALUE);
                parsedValue = true;
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            annotations = field.getDeclaredAnnotations();
            if (annotations.length == 0) {
                continue;
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof CacheKey && !parsedKey) {
                    cacheField.setKey(field.getName());
                    parsedKey = true;
                }
                if (annotation instanceof CacheValue && !parsedValue) {
                    cacheField.setValue(field.getName());
                    parsedValue = true;
                }
            }
            //解析到了就终止循环
            if (parsedKey && parsedValue) {
                break;
            }
        }
        String className = clazz.getName();
        if (!StringUtils.hasText(cacheField.getKey())) {
            throw new CacheAnnotationIllegalException(className + "中cacheKey不存在");
        }

        if (!StringUtils.hasText(cacheField.getValue())) {
            throw new CacheAnnotationIllegalException(className + "中cacheValue不存在");
        }
        fieldCaches.putIfAbsent(className, cacheField);
        return fieldCaches.get(className);
    }

    /**
     * 得到缓存key的值
     */
    public Object getCacheKey(Object o) {
        CacheField cacheField = getObjectCacheField(o);
        return getFieldValue(cacheField.getKey(), o);
    }


    public Object getCacheValue(Object o) {
        CacheField cacheField = getObjectCacheField(o);
        if (THIS_VALUE.equals(cacheField.getValue())) {
            return o;
        }
        return getFieldValue(cacheField.getValue(), o);
    }

    /**
     * 得到对象的字段值
     *
     * @param fieldName 字段名称
     * @param object    对象示例
     * @return 字段值
     */
    public Object getFieldValue(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //暴力反射
            field.setAccessible(true);
            return ReflectionUtils.getField(field, object);
        } catch (Exception e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }

    /**
     * 将值解析成map
     */
    public Map<Object, Object> parseValue2Map(String cacheName, String split, Object value) {
        if (value instanceof Map) {
            Map<Object, Object> cacheMap = new HashMap<>(((Map<?, ?>) value).size());
            ((Map<?, ?>) value).forEach((k, v) -> {
                String cacheKey = String.join(split, cacheName, k.toString());
                cacheMap.put(cacheKey, value);
            });
            return cacheMap;
        }
        //将list通过注解转map
        if (value instanceof Collection) {
            Collection<?> values = (Collection<?>) value;
            Map<Object, Object> cacheData = new HashMap<>(values.size());
            for (Object o : values) {
                Object cacheKey = this.getCacheKey(o);
                Object cacheValue = this.getCacheValue(o);
                cacheData.put(String.join(split, cacheName, cacheKey.toString()), cacheValue);
            }
            return cacheData;
        }
        throw new IllegalArgumentException("cacheValue有误,必须属于map或者Collection");
    }


    /**
     * 将值解析成map
     */
    public Map<Object, Object> parseValue2Map(Object value) {
        if (value instanceof Map) {
            Map<Object, Object> cacheMap = new HashMap<>(((Map<?, ?>) value).size());
            cacheMap.putAll((Map<?, ?>) value);
            return cacheMap;
        }
        //将list通过注解转map
        if (value instanceof Collection) {
            Collection<?> values = (Collection<?>) value;
            Map<Object, Object> cacheData = new HashMap<>(values.size());
            for (Object o : values) {
                Object cacheKey = this.getCacheKey(o);
                Object cacheValue = this.getCacheValue(o);
                cacheData.put(cacheKey, cacheValue);
            }
            return cacheData;
        }
        throw new IllegalArgumentException("cacheValue有误,必须属于map或者Collection");
    }

    /**
     * 解析缓存key
     *
     * @param cacheName 缓存名称
     */
    public Collection<Object> parseCacheKeys(String cacheName, String split, Object keys) {
        if (keys instanceof Collection) {
            Collection<Object> cacheKeys = new ArrayList<>(((Collection<?>) keys).size());
            ((Collection<?>) keys).forEach(key -> {
                if (key instanceof String || key instanceof Number) {
                    cacheKeys.add(String.join(split, cacheName, key.toString()));
                } else {
                    cacheKeys.add(String.join(split, cacheName, this.getCacheKey(key).toString()));
                }
            });
            return cacheKeys;
        }
        throw new IllegalArgumentException("keys类型不匹配");
    }

    public Collection<Object> parseCacheKeys(Object keys) {
        if (keys instanceof Collection) {
            Collection<Object> cacheKeys = new ArrayList<>(((Collection<?>) keys).size());
            ((Collection<?>) keys).forEach(key -> {
                if (key instanceof String || key instanceof Number) {
                    cacheKeys.add(key.toString());
                } else {
                    cacheKeys.add(this.getCacheKey(key));
                }
            });
            return cacheKeys;
        }
        throw new IllegalArgumentException("keys类型不匹配");
    }
}
