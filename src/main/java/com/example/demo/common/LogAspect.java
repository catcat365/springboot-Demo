package com.example.demo.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Around("@annotation(com.example.demo.common.Log)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 1. 获取方法上的注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        String description = logAnnotation.value();

        // 2. 获取请求参数
        Object[] args = joinPoint.getArgs();
        Object[] filteredArgs = Arrays.stream(args)
                .filter(arg -> !(arg instanceof MultipartFile))
                .toArray();  //TODO 暂时先过滤掉文件,否则会报错

        String params = objectMapper.writeValueAsString(filteredArgs);

        // 3. 获取请求 URL
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();

        // 4. 执行目标方法
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            log.error("[{}] 接口异常: {} | 参数: {} | 异常信息: {}", description, url, params, e.getMessage());
            throw e; // 异常继续抛出，交给全局异常处理器
        } finally {
            // 5. 记录执行耗时
            long duration = System.currentTimeMillis() - startTime;
            String resultStr = (result != null) ? objectMapper.writeValueAsString(result) : "null";
            log.info("[{}] 接口: {} | 耗时: {}ms | 参数: {} | 返回: {}", description, url, duration, params, resultStr);
        }
    }
}
