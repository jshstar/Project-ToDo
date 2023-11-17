package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAllToDoResponseDto {
    private String title;

    private String user;

    private LocalDateTime createAt;

    private boolean complete;

    public GetAllToDoResponseDto(ToDoCard card)
    {
        this.title = card.getTitle();
        this.user = card.getUser().getUsername();
        this.createAt = card.getCreatedAt();
        this.complete = card.isComplete();
    }
}
