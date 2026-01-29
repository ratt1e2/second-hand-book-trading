package com.booktrading.server.service.impl;

import com.booktrading.server.entity.User;
import com.booktrading.server.mapper.UserMapper;
import com.booktrading.server.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.booktrading.server.common.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String phone, String password) {
        User user = getByPhone(phone);
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException(400, "账号已被禁用");
        }
        // 这里应该使用密码加密验证，暂时简化处理
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(400, "密码错误");
        }
        return user;
    }

    @Override
    public User register(User user) {
        if (getByPhone(user.getPhone()) != null) {
            throw new BusinessException(400, "手机号已注册");
        }
        save(user);
        return user;
    }

    @Override
    public User getByOpenid(String openid) {
        return lambdaQuery().eq(User::getOpenid, openid).one();
    }

    @Override
    public User getByPhone(String phone) {
        return lambdaQuery().eq(User::getPhone, phone).one();
    }

    @Override
    public void updateStatus(Long userId, Integer status) {
        lambdaUpdate().eq(User::getId, userId).set(User::getStatus, status).update();
    }
}
