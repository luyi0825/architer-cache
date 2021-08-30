package io.github.architers.cache.batch;

import com.sun.xml.internal.txw2.IllegalAnnotationException;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luyi
 * @version 1.0.0
 * 批量值的解析
 */
public class BatchValueParser {
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
    public Map<Object, Object> parse2MapValue(Object value) {
        if (value instanceof Map) {
            return (Map<Object, Object>) value;
        }
        if (value instanceof Collection) {
            Collection<Object> values = (Collection<Object>) value;
            Map<Object, Object> cacheData = new HashMap<>();
            for (Object o : values) {
                Class<?> clazz = o.getClass();
                String className = clazz.getName();
                CacheField cacheField = fieldCaches.get(className);
                if (cacheField == null) {
                    cacheField = this.getObjectCacheField(o);
                }
                Object cacheKey = getFieldValue(cacheField.getKey(), o);
                Object cacheValue = getFieldValue(cacheField.getValue(), o);
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
        for (Field field : clazz.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length == 0) {
                continue;
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof CacheKey) {
                    cacheField.setKey(field.getName());
                }
                if (annotation instanceof CacheValue) {
                    cacheField.setValue(field.getName());
                }
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

    private Object getFieldValue(String fieldName, Object o) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            return ReflectionUtils.getField(field, o);
        } catch (Exception e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }

    /**
     * 缓存字段
     */
    static class CacheField {
        /**
         * key对应的字段
         */
        private String key;
        /**
         * value对应的字段
         */
        private String value;

        public String getKey() {
            return key;
        }

        public CacheField setKey(String key) {
            this.key = key;
            return this;
        }

        public String getValue() {
            return value;
        }

        public CacheField setValue(String value) {
            this.value = value;
            return this;
        }
    }

    ;

}
