package com.sparta.project_todo.controller;


import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.dto.ToDoResponseDto;
import com.sparta.project_todo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final ToDoService boardService;

    public ToDoController(ToDoService boardService){this.boardService = boardService;}

    // 게시글 작성
    @PostMapping("/todo")
    public ResponseEntity<?> createCard(@RequestBody ToDoRequestDto requestDto) {
        try{
            return new ResponseEntity<>(boardService.createCard(requestDto),HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("잘못된 양식입니다.",HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 목록 조회
    @GetMapping("/todo")
    public List<ToDoResponseDto> getCards(){
        return boardService.getCards();
    }

    // 선택된 게시글 조회
    @GetMapping("/todo/{bNum}")
    public ResponseEntity<?> selectGetCard(@PathVariable Long bNum){
        try
        {
            return new ResponseEntity<>(boardService.selectGetCards(bNum),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("찾으시는 게시글이 없습니다.",HttpStatus.NOT_FOUND);
        }

    }

    // 게시글 업데이트
    @PutMapping("/todo/{bNum}")
    public ResponseEntity<?> updateCard(@PathVariable Long bNum, @RequestBody ToDoRequestDto requestDto) {
        return new ResponseEntity<>(boardService.updateCard(bNum,requestDto),HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/todo/{bNum}/{pw}")
    public ResponseEntity<?> deleteCard(@PathVariable Long bNum, @PathVariable String pw) {
        return new ResponseEntity<>("작성하신 "+ boardService.deleteBoard(bNum) + " 번째 게시글이 정상적으로 삭제됐습니다.", HttpStatus.OK);

    }


}