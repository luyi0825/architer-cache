package com.architer.cache.context.lock;

public class TestService {

    //@Cacheable(key = "", LOCK = @Locked())
    public String getById(String id) {
        return "55";
    }

}
