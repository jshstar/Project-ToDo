package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {
    private final Long id; // 댓글 id

    private final String comment; // 댓글 내용

    private final LocalDateTime createAt; // 생성 시간

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createAt = comment.getCreatedAt();
    }

}
