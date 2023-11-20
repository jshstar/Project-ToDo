package com.sparta.project_todo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ToDoRequestDto {
    @NotBlank(message = "제목을 작성해주세요.")
    private String title; // 카드 제목

    private String contents; // 카드 내용
}
