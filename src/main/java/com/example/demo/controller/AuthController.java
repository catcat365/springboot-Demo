package com.example.demo.controller;

import com.example.demo.common.JwtUtils;
import com.example.demo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证模块", description = "登录与Token获取")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "传入用户名密码，返回JWT Token")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 简单模拟验证（实际项目应查数据库并比对加密后的密码）
        if ("admin".equals(username) && "123456".equals(password)) {

            // 这里报错，有个io.jsonwebtoken.security.WeakKeyException
            String token = JwtUtils.generateToken(username);

            System.out.println("需要 在http://localhost:8080/doc.html#/documentManager/GlobalParameters$default  全局参数设置 设置 Authorization 头， 里面是Bearer +token (我打印出来了） 第1次去验证的时候得到参数，之后去全局参数里面设置，否则其他请求不会通过" );
            System.out.println("Bearer " + token);
            return Result.success(Map.of("token", token));
        }
        return Result.fail("401 - 用户名或密码错误");
    }
}