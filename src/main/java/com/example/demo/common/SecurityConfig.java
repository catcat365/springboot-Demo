package com.example.demo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // todo 这个方法 废弃了， 后续需要改下
        http.csrf().disable() // 前后端分离通常关闭 CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态，不使用 Session
                .and().authorizeHttpRequests()
                // 放行登录接口、Swagger 文档接口
                .requestMatchers("/api/auth/**", "/doc.html", "/webjars/**", "/v3/api-docs/**").permitAll()
                // 其余所有接口都需要认证
                .anyRequest().authenticated().and().addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}