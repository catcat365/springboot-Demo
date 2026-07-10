package com.example.demo.controller;


import com.example.demo.common.Log;
import com.example.demo.common.Result;
import com.example.demo.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@Tag(name = "文件管理模块", description = "包含文件上传接口")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @Log("上传文件")
    @Operation(summary = "上传头像/图片", description = "接收 MultipartFile 文件并保存到本地")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = fileService.uploadFile(file);
        return Result.success(fileUrl);
    }
}