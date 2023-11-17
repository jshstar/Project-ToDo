package com.sparta.project_todo.repository;

import com.sparta.project_todo.entity.ToDoCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDoCard, Long> {
    List<ToDoCard> findAllByOrderByCreatedAtDesc();

    List<ToDoCard> findByCompleteFalseOrderByCreatedAtDesc();
}
