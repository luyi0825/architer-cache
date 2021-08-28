package com.architecture.redis;

import java.io.Serializable;


public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
