package io.github.architers.cache.delete;

import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.DeleteCache;

public interface DeleteService {
    @DeleteCache(cacheName = "oneDelete", key = "#userInfo.username")
    void oneDelete(UserInfo userInfo);
}
