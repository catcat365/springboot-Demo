package com.example.demo.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)// 只能加在方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时生效
public @interface Log {
    String value() default  ""; // 接口描述，例如 "查询用户列表"
}
