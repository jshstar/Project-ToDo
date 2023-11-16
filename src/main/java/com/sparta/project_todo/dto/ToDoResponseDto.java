package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ToDoResponseDto {
    private Long bNum;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private Boolean complete;
    public ToDoResponseDto(ToDoCard card) {
        this.bNum = card.getBNum();
        this.title = card.getTitle();
        this.username = card.getUser().getUsername();
        this.contents = card.getContents();
        this.createdAt = card.getCreatedAt();
        this.complete = card.isComplete();
    }


}
