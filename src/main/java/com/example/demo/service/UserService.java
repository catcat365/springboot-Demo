package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

//    private final List<User> userList = new ArrayList<>();
//    public UserService() {
//        userList.add(new User(1L, "张三", "zhansan@example.com"));
//        userList.add(new User(2L, "李四", "lisi@example.com"));
//    }


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();  // 一行代码查询所有
    }


    public User saveUser(User user) {
        return userRepository.save(user); // 一行代码保存（如果 id 为空则新增，不为空则更新）
    }


    // 更新用户
    public User updateUser(Long id, User userVo) {

        // 1. 先根据 ID 查询，如果不存在则抛出异常
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户ID 不存在" + id));


        // 2. 更新非空字段
        user.setName(userVo.getName());
        user.setEmail(userVo.getEmail());

        // 3. 保存更新后的数据

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("删除失败，用户ID" + id);
        }

        userRepository.deleteById(id);
    }
}
