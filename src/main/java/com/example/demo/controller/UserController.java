package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;


    // 通过构造器注入 Service
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 访问 http://localhost:8080/api/users 即可获取 JSON 列表
    @GetMapping
    public List<User> listUsers() {
        List<User> allUsers = userService.getAllUsers();
        return Result.success(allUsers).getData();  // 🌟 包装为统一格式
    }


    // 🌟 新增：创建用户的 POST 接口
    @PostMapping
    public User createUser(@RequestBody User user) {
        // @RequestBody 会自动将前端传来的 JSON 字符串转换为 User 对象
        User user1 = userService.saveUser(user);
        return Result.success(user1).getData();
    }


    // 🌟 更新用户 (PUT)
// 路径示例: /api/users/1
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User user1 = userService.updateUser(id, user);
        return Result.success(user1).getData();
    }


    // 🌟 删除用户 (DELETE)
// 路径示例: /api/users/1
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return Result.success("删除用户成功，ID=" + id).getData(); // 🌟 包装为统一格式
    }
}
