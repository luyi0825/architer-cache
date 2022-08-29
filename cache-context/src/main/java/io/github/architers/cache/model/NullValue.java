package io.github.architers.model;

import java.io.Serializable;

/**
 * 空值
 * <li>防止缓存穿透用的类</li>
 * <li>防止重复查询</li>
 *
 * @author luyi
 * @version 1.0.0
 */
public class NullValue implements Serializable {
    public static final NullValue INVALID_CACHE = new NullValue();
    private String value = "NULL";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
