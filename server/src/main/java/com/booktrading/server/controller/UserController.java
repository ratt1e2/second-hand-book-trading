package com.booktrading.server.controller;

import com.booktrading.server.common.Result;
import com.booktrading.server.entity.User;
import com.booktrading.server.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestParam Long userId) {
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> getUserList(@RequestParam(defaultValue = "1") Integer page, 
                                 @RequestParam(defaultValue = "10") Integer size) {
        IPage<User> userPage = new Page<>(page, size);
        userPage = userService.page(userPage);
        return Result.success(userPage);
    }

    @PutMapping("/status")
    public Result<?> updateUserStatus(@RequestParam Long userId, @RequestParam Integer status) {
        userService.updateStatus(userId, status);
        return Result.success();
    }
}
