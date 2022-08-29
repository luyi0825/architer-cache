package io.github.architers.cache.yali;

import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.Cacheable;

public interface StressService {

    UserInfo findByNameDirect(String name);

    @Cacheable(cacheName = "findByName", key = "#name")
    UserInfo findByNameAnnotation(String name);
}
