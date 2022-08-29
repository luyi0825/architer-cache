package io.github.architers.yali;

import io.github.architers.UserInfo;
import io.github.architers.annotation.Cacheable;

public interface StressService {

    UserInfo findByNameDirect(String name);

    @Cacheable(cacheName = "findByName", key = "#name")
    UserInfo findByNameAnnotation(String name);
}
