package com.sparta.project_todo.dto;

import com.sparta.project_todo.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long bNum;
    private String title;
    private String userName;
    private String contents;
    private LocalDateTime createdAt;
    public BoardResponseDto(Board board)
    {
        this.bNum = board.getBNum();
        this.title = board.getTitle();
        this.userName = board.getUserName();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
    }


}
