package io.github.architers.cache.model;

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
}
