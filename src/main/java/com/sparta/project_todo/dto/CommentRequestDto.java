package com.sparta.project_todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "댓글을 작성해 주세요")
    private final String comment; // 댓글 내용
}
