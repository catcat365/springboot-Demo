package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {

//    private final List<User> userList = new ArrayList<>();
//    public UserService() {
//        userList.add(new User(1L, "张三", "zhansan@example.com"));
//        userList.add(new User(2L, "李四", "lisi@example.com"));
//    }


    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    // 定义缓存的 Key 前缀和过期时间
    private static final String USER_CACHE_KEY = "user:all";
    private static final long CACHE_EXPIRE_TIME = 3600L; // 缓存 1 小时


    public UserService(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<User> getAllUsers() {

        // 1. 先从缓存中查
        Object cachedData = redisTemplate.opsForValue().get(USER_CACHE_KEY);
        if (cachedData != null) {
            System.out.println("🚀 命中 Redis 缓存，未查询数据库！");
            return (List<User>) cachedData;
        }

        // 2. 缓存中没有，查询数据库
        System.out.println("🗄️ 缓存未命中，正在查询数据库...");
        List<User> users = userRepository.findAll();
        // 3. 将查到的数据放入缓存，并设置过期时间
        redisTemplate.opsForValue().set(USER_CACHE_KEY, users, CACHE_EXPIRE_TIME, TimeUnit.SECONDS);

        return users;
    }


    public User saveUser(User user) {
        User save = userRepository.save(user);
        redisTemplate.delete(USER_CACHE_KEY);
        return save; // 一行代码保存（如果 id 为空则新增，不为空则更新）
    }


    // 更新用户
    public User updateUser(Long id, User userVo) {

        // 1. 先根据 ID 查询，如果不存在则抛出异常
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("用户ID 不存在" + id));


        // 2. 更新非空字段
        user.setName(userVo.getName());
        user.setEmail(userVo.getEmail());

        // 3. 保存更新后的数据

        User save = userRepository.save(user);

        redisTemplate.delete(USER_CACHE_KEY);
        return save;
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("删除失败，用户ID" + id);
        }

        userRepository.deleteById(id);
        redisTemplate.delete(USER_CACHE_KEY);
    }

    // 🌟 新增：分页查询方法
    public Page<User> getUsersByPage(int page, int size) {
        // PageRequest.of(page, size) 构建分页请求对象
        // 注意：JPA 的页码是从 0 开始的，0 代表第一页
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest);
    }
}
