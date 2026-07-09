package com.example.demo.common;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 专门拦截 RuntimeException 及其子类
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        // 这里可以打印日志，方便排查问题

        e.printStackTrace();
        // 根据异常信息返回不同的提示（这里简单演示）

        if (e.getMessage().contains("不存在")) {
            return Result.fail("404 - " + e.getMessage());
        }

        return Result.fail("500 - 系统内部错误" + e.getMessage());
    }

}
