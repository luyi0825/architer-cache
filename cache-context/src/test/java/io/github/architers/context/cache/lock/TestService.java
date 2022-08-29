package io.github.architers.cache.context.cache.lock;

public class TestService {

    //@Cacheable(key = "", LOCK = @Locked())
    public String getById(String id) {
        return "55";
    }

}
