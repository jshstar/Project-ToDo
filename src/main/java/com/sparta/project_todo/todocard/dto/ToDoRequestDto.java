package com.sparta.project_todo.todocard.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ToDoRequestDto {
    @NotBlank(message = "제목을 작성해주세요.")
    private final String title; // 카드 제목

    private final String contents; // 카드 내용

}
