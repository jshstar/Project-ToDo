package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

@Getter
public class CompleteToDoResponseDto {
    private Long bNum;
    private boolean complete;

    public CompleteToDoResponseDto(ToDoCard card){
        this.bNum = card.getBNum();
        this.complete = card.isComplete();
    }
}
