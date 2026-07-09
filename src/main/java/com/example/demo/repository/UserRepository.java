package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    // 这里什么都不用写！
    // JpaRepository 已经自动帮你提供了 save(), findById(), findAll(), deleteById() 等所有基础 CRUD 方法。
}
