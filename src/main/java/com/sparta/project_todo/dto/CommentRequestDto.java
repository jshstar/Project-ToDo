package com.sparta.project_todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글을 작성해 주세요")
    private String comment; // 댓글 내용
}
