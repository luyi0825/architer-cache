package io.github.architers.batch;

/**
 * 批量缓存的缓存字段
 *
 * @author luyi
 * @version 1.0.0
 */
public class CacheField {
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

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
