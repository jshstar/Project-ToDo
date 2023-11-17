package com.sparta.project_todo.dto;


import com.sparta.project_todo.entity.ToDoCard;
import lombok.Getter;

@Getter
public class HiddenToDoResponseDto {

    private Long id;

    private boolean hidden;

    public HiddenToDoResponseDto(ToDoCard card) {
        this.id = card.getId();
        this.hidden = card.isHidden();
    }
}
