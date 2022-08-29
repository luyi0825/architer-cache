package io.github.architers;

import io.github.architers.batch.CacheKey;
import io.github.architers.batch.CacheValue;

import java.io.Serializable;
import java.util.UUID;

@CacheValue
public class UserInfo implements Serializable {
    @CacheKey
    private String username;
    @CacheValue
    private String password;
    private String phone;

    public static UserInfo getRandomUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(UUID.randomUUID().toString());
        userInfo.setPassword("testPassword");
        int num = (int) (Math.random() * 10);
        userInfo.setPhone(StringUtil.repeat((num + ""), 11));
        return userInfo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public UserInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }
}
