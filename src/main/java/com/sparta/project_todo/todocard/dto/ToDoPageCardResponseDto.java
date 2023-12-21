package com.sparta.project_todo.todocard.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.sparta.project_todo.todocard.entity.ToDoCard;

import lombok.Getter;

@Getter
public class ToDoPageCardResponseDto {

	private final Long toDoId; // 카드 id

	private final String title; // 카드 제목

	private final String username; // 유저 이름

	private final String contents; // 내용

	private final LocalDateTime createdAt; // 생성 시간

	private final LocalDateTime modifiedAt; // 수정 시간

	public ToDoPageCardResponseDto(ToDoCard card) {
		this.toDoId = card.getId();
		this.title = card.getTitle();
		this.username = card.getUser().getUsername();
		this.contents = card.getContents();
		this.createdAt = card.getCreatedAt();
		this.modifiedAt = card.getModifiedAt();
	}

}
