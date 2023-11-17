package com.sparta.project_todo.repository;

import com.sparta.project_todo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
