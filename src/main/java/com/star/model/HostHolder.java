package com.star.model;

import com.star.model.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Author Abner
 * @CreateDate 2020/4/12
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
