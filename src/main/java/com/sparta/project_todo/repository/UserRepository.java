package com.sparta.project_todo.repository;


import com.sparta.project_todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName); // 같은 유저 이름 찾기
}