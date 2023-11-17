package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;

    private String comment;

    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.createAt = comment.getCreatedAt();
    }

}
