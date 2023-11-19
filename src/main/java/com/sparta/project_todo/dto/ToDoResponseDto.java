package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ToDoResponseDto {
    private Long toDoId; // 카드 id

    private String title; // 카드 제목

    private String username; // 유저 이름

    private String contents; // 내용

    private LocalDateTime createdAt; // 생성 시간

    private Boolean complete; // 완료 여부

    public ToDoResponseDto(ToDoCard card) {
        this.toDoId = card.getId();
        this.title = card.getTitle();
        this.username = card.getUser().getUsername();
        this.contents = card.getContents();
        this.createdAt = card.getCreatedAt();
        this.complete = card.isComplete();
    }


}
