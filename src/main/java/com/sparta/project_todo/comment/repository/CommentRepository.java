package com.sparta.project_todo.comment.repository;

import com.sparta.project_todo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
