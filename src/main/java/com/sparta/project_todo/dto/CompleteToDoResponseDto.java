package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

@Getter
public class CompleteToDoResponseDto {
    private Long toDoId; // 카드 id

    private boolean complete; // 완료 여부

    public CompleteToDoResponseDto(ToDoCard card){
        this.toDoId = card.getId();
        this.complete = card.isComplete();
    }
}
