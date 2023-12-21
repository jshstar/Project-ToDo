package com.sparta.project_todo.comment.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import com.sparta.project_todo.comment.entity.Comment;

import lombok.Getter;

@Getter
public class CommentPageResponseDto {
	private final Slice<CommentResponseDto> commentResponseDtoPage;

	public CommentPageResponseDto(Slice<Comment> commnetPage){
		this.commentResponseDtoPage = commnetPage.map(CommentResponseDto::new);
	}
}
