package com.booktrading.server.service;

import com.booktrading.server.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User login(String phone, String password);
    User register(User user);
    User getByOpenid(String openid);
    User getByPhone(String phone);
    void updateStatus(Long userId, Integer status);
}
