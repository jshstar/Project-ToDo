package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId; // 댓글 id

    private String comment; // 댓글 내용

    private LocalDateTime createAt; // 생성 시간

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.createAt = comment.getCreatedAt();
    }

}
