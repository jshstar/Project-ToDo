package com.sparta.project_todo.user.repository;


import com.sparta.project_todo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName); // 같은 유저 이름 찾기

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.emailCheck = true where u.email = :email and u.emailCheck = false ")
    void successVerify(String email);
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from User u where u.email = :email and u.emailCheck = false")
    void failVerify(String email);
}
