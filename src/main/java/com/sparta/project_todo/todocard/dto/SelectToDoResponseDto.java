package com.sparta.project_todo.todocard.dto;

import com.sparta.project_todo.todocard.entity.ToDoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SelectToDoResponseDto {

    private String title; // 제목

    private String username; // 작성자

    private String contents; // 내용

    private LocalDateTime createdAt; // 생성시간

    public SelectToDoResponseDto(ToDoCard card) {
        this.title = card.getTitle();
        this.username = card.getUser().getUsername();
        this.contents = card.getContents();
        this.createdAt = card.getCreatedAt();

    }

}
