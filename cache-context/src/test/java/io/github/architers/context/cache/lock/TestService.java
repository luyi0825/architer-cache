package io.github.architers.context.cache.lock;

public class TestService {

    //@Cacheable(key = "", LOCK = @Locked())
    public String getById(String id) {
        return "55";
    }

}
