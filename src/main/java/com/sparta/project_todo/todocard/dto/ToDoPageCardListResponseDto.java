package com.sparta.project_todo.todocard.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sparta.project_todo.todocard.entity.ToDoCard;

import lombok.Getter;

@Getter
public class ToDoPageCardListResponseDto {
	private final Page<ToDoPageCardResponseDto> pageList;

	public ToDoPageCardListResponseDto(Page<ToDoCard> pageList){
		this.pageList = pageList.map(ToDoPageCardResponseDto::new);
	}


}
