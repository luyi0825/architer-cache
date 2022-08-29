package io.github.architers.cache.expression;

import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.PutCache;

public interface CacheExpressionService {
    /**
     * 1.使用共有字段
     * 2.使用共有方法
     * 3.使用私有字段
     */
    @PutCache(cacheName = "expression_1_publicField", key = "#userInfo.username", cacheValue = "#root.target.publicField")
    @PutCache(cacheName = "expression_2_publicMethod", key = "#userInfo.username", cacheValue = "#root.target.publicMethod()")
    @PutCache(cacheName = "expression_3_privateField", key = "#userInfo.username", cacheValue = "#root.fieldValue('privateField')")
    void root(UserInfo userInfo);
}
