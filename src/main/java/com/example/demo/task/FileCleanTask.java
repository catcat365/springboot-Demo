package com.example.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FileCleanTask {

    private static final Logger log = LoggerFactory.getLogger(FileCleanTask.class);

    @Value("${file.upload.path}")
    private String uploadPath;


    /**
     * 🌟 每天凌晨 2:00 执行一次清理任务
     * Cron 表达式："秒 分 时 日 月 周"
     * "0 0 2 * * ?" 表示：每天凌晨 2点 0分 0秒
     */
    @Scheduled(cron = "0 0 2 * * ?")
    // 临时测试用：每 5 秒执行一次
//    @Scheduled(cron = "0/5 * * * * ?")
    public void cleanExpiredFiles() {
        log.info("⏰ 定时任务启动：开始清理过期文件...");

        File dir = new File(uploadPath);
        if (!dir.exists() || !dir.isDirectory()) {
            log.warn("上传目录不存在，跳过清理: {}", uploadPath);
            return;
        }

        int deletedCount = 0;
        // 获取当前时间往前推 7 天的时间点（这里假设超过 7 天的文件视为过期）
        long expireThreshold = Instant.now().minus(7, ChronoUnit.DAYS).toEpochMilli();

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                log.info("🗑️ 扫描到的文件: {} 最后修改时间 {}", file.getName(),file.lastModified());

                // 如果文件的最后修改时间早于 7 天前，则删除
                if (file.isFile() && file.lastModified() < expireThreshold) {
//                    if (file.delete()) {
//                        deletedCount++;
//                        log.info("🗑️ 已删除过期文件: {}", file.getName());
//                    }

                }
            }
        }
        log.info("✅ 定时任务结束：共清理了 {} 个过期文件", deletedCount);
    }
}
