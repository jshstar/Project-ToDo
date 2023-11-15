package com.sparta.project_todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)// 이넘 사용시 데이터 저장 어노테이션
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username= username;
        this.password= password;
        this.role = role;
    }
}