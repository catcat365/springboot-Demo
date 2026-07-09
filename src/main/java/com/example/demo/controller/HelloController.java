package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController 标识这是一个处理 RESTful 请求的控制器，返回值会自动转为 JSON 或纯文本
@RestController
public class HelloController {

    // @GetMapping 映射 GET 请求，路径为 /hello
    @GetMapping("/hello")
    public String sayHello() {
        return "🎉 恭喜！你的第一个 Spring Boot 接口已成功运行！";
    }
}