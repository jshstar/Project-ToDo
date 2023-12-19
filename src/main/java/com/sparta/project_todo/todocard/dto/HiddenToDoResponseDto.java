package com.sparta.project_todo.todocard.dto;


import com.sparta.project_todo.todocard.entity.ToDoCard;
import lombok.Getter;

@Getter
public class HiddenToDoResponseDto {

    private Long id; // ToDo카드 Id

    private boolean hidden; // 비공개 여부

    public HiddenToDoResponseDto(ToDoCard card) {
        this.id = card.getId();
        this.hidden = card.isHidden();
    }
}
