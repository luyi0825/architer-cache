package io.github.architers.context.model;

import java.io.Serializable;

/**
 * 无效的缓存，防止缓存穿透用的类
 *
 * @author luyi
 */
public class InvalidCache implements Serializable {
    public static final InvalidCache INVALID_CACHE = new InvalidCache();
}
