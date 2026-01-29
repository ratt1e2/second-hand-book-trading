package com.booktrading.server.controller;

import com.booktrading.server.common.Result;
import com.booktrading.server.common.JwtUtils;
import com.booktrading.server.entity.User;
import com.booktrading.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        
        User user = userService.login(phone, password);
        
        // 生成token
        String token = jwtUtils.generateToken(user.getId(), user.getPhone());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return Result.success(registeredUser);
    }

    @PostMapping("/wechat/login")
    public Result<?> wechatLogin(@RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String nickname = params.get("nickname");
        String avatar = params.get("avatar");
        
        User user = userService.getByOpenid(openid);
        if (user == null) {
            // 新用户，自动注册
            user = new User();
            user.setOpenid(openid);
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setRole(0);
            user.setStatus(1);
            userService.save(user);
        }
        
        // 生成token
        String token = jwtUtils.generateToken(user.getId(), user.getPhone());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        
        return Result.success(data);
    }
}
