package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ToDoResponseDto {
    private Long toDoId;

    private String title;

    private String username;

    private String contents;

    private LocalDateTime createdAt;

    private Boolean complete;

    public ToDoResponseDto(ToDoCard card) {
        this.toDoId = card.getId();
        this.title = card.getTitle();
        this.username = card.getUser().getUsername();
        this.contents = card.getContents();
        this.createdAt = card.getCreatedAt();
        this.complete = card.isComplete();
    }


}
