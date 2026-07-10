package com.example.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    //     # 文件保存的本地绝对路径（请根据你电脑的实际路径修改）
    @Value("${file.upload.path}")
    private String uploadPath;

    //        # 访问文件的 URL 前缀
    @Value("${file.url.prefix}")
    private String urlPrefix;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /uploads/** 映射到本地真实的文件目录
        registry.addResourceHandler(urlPrefix + "**")
                .addResourceLocations("file:" + uploadPath);
    }

}
