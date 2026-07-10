package com.example.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    //     # 文件保存的本地绝对路径（请根据你电脑的实际路径修改）
    @Value("${file.upload.path}")
    private String uploadPath;

    //        # 访问文件的 URL 前缀
    @Value("${file.url.prefix}")
    private String urlPrefix;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        // 1. 获取原始文件名和后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".png";

        // 2. 生成唯一的新文件名，防止重名覆盖
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        // 3. 确保目录存在
        File destDir = new File(uploadPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        // 4. 保存文件到本地磁盘
        File destFile = new File(destDir, newFileName);
        file.transferTo(destFile);

        // 5. 返回可以通过网络访问的 URL
        return urlPrefix + newFileName;
    }
}
