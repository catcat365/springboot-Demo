-- 1. 创建项目对应的本地数据库，字符集适配你配置里的utf8
CREATE DATABASE IF NOT EXISTS springboot_db
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

-- 2. 创建仅允许本地登录的专属用户，禁止远程连接
CREATE USER 'springboot_db'@'localhost' IDENTIFIED BY 'springboot_db';

-- 3. 给本地用户分配springboot_db库的全部操作权限
GRANT ALL PRIVILEGES ON springboot_db.* TO 'springboot_db'@'localhost';

-- 4. 刷新权限让配置立即生效
FLUSH PRIVILEGES;



-- 创建表 User sql


CREATE TABLE `users` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                         `name` VARCHAR(255) DEFAULT NULL COMMENT '用户名',
                         `email` VARCHAR(255) DEFAULT NULL COMMENT '用户邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
