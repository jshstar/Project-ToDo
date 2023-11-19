package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAllToDoResponseDto {
    private String title; // 제목

    private String username; // 유저이름

    private LocalDateTime createAt; // 생성시간

    private boolean complete; // 완료여부

    public GetAllToDoResponseDto(ToDoCard card)
    {
        this.title = card.getTitle();
        this.username = card.getUser().getUsername();
        this.createAt = card.getCreatedAt();
        this.complete = card.isComplete();
    }
}
