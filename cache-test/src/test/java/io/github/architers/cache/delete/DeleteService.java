package io.github.architers.delete;

import io.github.architers.UserInfo;
import io.github.architers.annotation.DeleteCache;

public interface DeleteService {
    @DeleteCache(cacheName = "oneDelete", key = "#userInfo.username")
    void oneDelete(UserInfo userInfo);
}
