package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SelectToDoResponseDto {

    private String title;

    private String username;

    private String contents;

    private LocalDateTime createdAt;

    public SelectToDoResponseDto(ToDoCard card) {
        this.title = card.getTitle();
        this.username = card.getUser().getUsername();
        this.contents = card.getContents();
        this.createdAt = card.getCreatedAt();

    }

}
