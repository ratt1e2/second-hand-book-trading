package com.booktrading.server.service;

import com.booktrading.server.entity.User;
import com.booktrading.server.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister() {
        // 测试注册新用户
        User user = new User();
        user.setPhone("13800138001");
        user.setPassword("123456");
        user.setNickname("测试用户");
        user.setRole(0);
        user.setStatus(1);

        User registeredUser = userService.register(user);
        assertNotNull(registeredUser);
        assertEquals("13800138001", registeredUser.getPhone());
        assertEquals("测试用户", registeredUser.getNickname());
    }

    @Test
    public void testLogin() {
        // 测试登录
        User user = userService.login("13800138001", "123456");
        assertNotNull(user);
        assertEquals("管理员", user.getNickname());
    }

    @Test
    public void testGetByPhone() {
        // 测试通过手机号获取用户
        User user = userService.getByPhone("13800138000");
        assertNotNull(user);
        assertEquals("管理员", user.getNickname());
    }

    @Test
    public void testUpdateStatus() {
        // 测试更新用户状态
        User user = userService.getByPhone("13800138000");
        assertNotNull(user);

        // 禁用用户
        userService.updateStatus(user.getId(), 0);
        User disabledUser = userService.getById(user.getId());
        assertEquals(0, disabledUser.getStatus());

        // 启用用户
        userService.updateStatus(user.getId(), 1);
        User enabledUser = userService.getById(user.getId());
        assertEquals(1, enabledUser.getStatus());
    }
}
