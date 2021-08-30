package io.github.architers.cache.batch;

import com.sun.xml.internal.txw2.IllegalAnnotationException;
import org.springframework.lang.NonNull;
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
public class BatchValueParser {
    /**
     * 表示值为当前对象
     */
    private static final String THIS_VALUE = "this";
    /**
     * 缓存类的缓存字段信息
     */
    Map<String, CacheField> fieldCaches = new ConcurrentHashMap<>();

    /**
     * 解析成Map值
     *
     * @param value 值类型为map或者collection(如果为集合，对应的key比如标明CacheKey和CacheValue注解）
     * @return 缓存值解析，key为缓存key,value为缓存值
     */
    public Map<?, ?> parse2MapValue(Object value) {
        if (value instanceof Map) {
            return (Map<?, ?>) value;
        }
        //将list通过注解转map
        if (value instanceof Collection) {
            Collection<?> values = (Collection<?>) value;
            Map<Object, Object> cacheData = new HashMap<>(values.size());
            for (Object o : values) {
                Class<?> clazz = o.getClass();
                String className = clazz.getName();
                CacheField cacheField = fieldCaches.get(className);
                if (cacheField == null) {
                    cacheField = this.getObjectCacheField(o);
                }
                Object cacheKey = getFieldValue(cacheField.getKey(), o);
                Object cacheValue;
                if (THIS_VALUE.equals(cacheField.getValue())) {
                    cacheValue = o;
                } else {
                    cacheValue = getFieldValue(cacheField.getValue(), o);
                }
                cacheData.put(cacheKey, cacheValue);
            }
            return cacheData;
        }
        throw new IllegalArgumentException("cacheValue有误,必须属于map或者Collection");
    }

    /**
     * 通过反射得到对象的缓存字段信息
     *
     * @param object 对应的缓存对象
     * @return 缓存字段信息
     */
    private CacheField getObjectCacheField(Object object) {
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
            throw new IllegalAnnotationException(className + "中cacheKey不存在");
        }

        if (!StringUtils.hasText(cacheField.getValue())) {
            throw new IllegalAnnotationException(className + "中cacheValue不存在");
        }
        fieldCaches.putIfAbsent(className, cacheField);
        return fieldCaches.get(className);
    }

    /**
     * 得到对象的字段值
     *
     * @param fieldName 字段名称
     * @param object    对象示例
     * @return 字段值
     */
    private Object getFieldValue(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //暴力反射
            field.setAccessible(true);
            return ReflectionUtils.getField(field, object);
        } catch (Exception e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }


    public Set<?> parseCacheKey(@NonNull Object value) {
        if (value instanceof Map) {
            return ((Map<?, ?>) value).keySet();
        }
        if (value instanceof Collection) {
            Set<Object> keys = new HashSet<>(((Collection<?>) value).size());
            ((Collection<?>) value).forEach(e -> {
                CacheField cacheField = fieldCaches.get(value.getClass().getName());
                if (cacheField == null) {
                    cacheField = getObjectCacheField(value);
                }
                keys.add(getFieldValue(cacheField.getKey(), e));
            });
            return keys;
        }
        return null;
    }
}
