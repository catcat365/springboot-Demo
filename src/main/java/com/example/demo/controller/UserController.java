package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

@Tag(name = "用户管理模块", description = "包含用户的增删改查接口")// 🌟 模块说明
public class UserController {


    private final UserService userService;


    // 通过构造器注入 Service
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 访问 http://localhost:8080/api/users 即可获取 JSON 列表
    @GetMapping
    @Operation(summary = "获取所有用户", description = "查询数据库中的所有用户列表") // 🌟 接口说明
    public Result<List<User>> listUsers() {
        List<User> allUsers = userService.getAllUsers();
        return Result.success(allUsers);  // 🌟 包装为统一格式
    }

    // 新增用户 - 加上 @Valid
    // 🌟 新增：创建用户的 POST 接口
    @PostMapping
    @Operation(summary = "新增用户", description = "传入用户信息，保存用户")
    public Result<User> createUser(@Valid @RequestBody User user) {
        // @RequestBody 会自动将前端传来的 JSON 字符串转换为 User 对象
        User user1 = userService.saveUser(user);
        return Result.success(user1);
    }

    // 更新用户 - 加上 @Valid
    // 🌟 更新用户 (PUT)
// 路径示例: /api/users/1
    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "根据 ID 更新用户信息")
    public Result<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User user1 = userService.updateUser(id, user);
        return Result.success(user1);
    }


    // 🌟 删除用户 (DELETE)
// 路径示例: /api/users/1
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据 ID 删除指定用户")

    public Result<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return Result.success("删除用户成功，ID= " + id); // 🌟 包装为统一格式
    }


    // 🌟 新增：分页查询接口
    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询,默认从第1页，查询10个")
    public Result<Page<User>> getUsersByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<User> usersByPage = userService.getUsersByPage(page, size);
        return Result.success(usersByPage);
    }
}
