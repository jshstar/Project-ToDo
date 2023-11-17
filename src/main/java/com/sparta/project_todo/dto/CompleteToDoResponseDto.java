package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

@Getter
public class CompleteToDoResponseDto {
    private Long toDoId;

    private boolean complete;

    public CompleteToDoResponseDto(ToDoCard card){
        this.toDoId = card.getId();
        this.complete = card.isComplete();
    }
}
