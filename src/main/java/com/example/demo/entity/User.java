package com.example.demo.entity;


import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {


    @Id  // 标记为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;
    private String name;
    private String email;



    // 必须提供一个无参构造器（JPA 要求）
    public User() {}

    // 构造器
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter 方法 (Spring Boot 内置 Jackson，会自动将对象转为 JSON)

    // Getter 和 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
