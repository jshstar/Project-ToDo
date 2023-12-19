package com.sparta.project_todo.user.repository;


import com.sparta.project_todo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName); // 같은 유저 이름 찾기
}
