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

        http
                // 关闭csrf防护（前后端分离项目常用）
                .csrf(csrf -> csrf.disable())
                // 跨域配置
                .cors(cors -> cors.configure(http))
                // 会话管理策略，这里配置成无状态适配JWT场景
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置请求权限规则
                .authorizeHttpRequests(auth -> auth
                        // 放行登录接口、公开接口
                        //全部先放行
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/login", "/public/**","/api/auth/**", "/doc.html", "/webjars/**", "/v3/api-docs/**").permitAll()
                        // 其余所有请求都需要认证
                        .anyRequest().authenticated()
                )
                // 接入你自定义的JWT过滤器，放在用户名密码认证过滤器之前
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}