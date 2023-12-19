package com.sparta.project_todo.todocard.controller;


import com.sparta.project_todo.todocard.dto.GetAllToDoResponseDto;
import com.sparta.project_todo.todocard.dto.ToDoRequestDto;
import com.sparta.project_todo.security.UserDetailsImpl;
import com.sparta.project_todo.todocard.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService boardService) {
        this.toDoService = boardService;
    }

    // 게시글 작성
    @PostMapping("/todo")
    public ResponseEntity<?> createCard(@RequestBody ToDoRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return new ResponseEntity<>(toDoService.createCard(requestDto, userDetails.getUser()), HttpStatus.CREATED);
    }

    // 게시글 목록 조회
    @GetMapping("/todo")
    public ResponseEntity<?> getCards(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<GetAllToDoResponseDto> responseDto = toDoService.getCards(userDetails);

        return ResponseEntity.ok(responseDto);
    }

    // 선택된 카드 조회
    @GetMapping("/todo/{id}")
    public ResponseEntity<?> selectGetCard(@PathVariable Long id) {

        return ResponseEntity.ok(toDoService.selectGetCards(id));
    }

    // 제목으로 카드목록 조회
    @GetMapping("/todo/title")
    public ResponseEntity<?> getTitleCard(@RequestParam(value = "title") String title,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return new ResponseEntity<>(toDoService.getTitleCards(title, userDetails), HttpStatus.OK);
    }

    // 카드 업데이트
    @PutMapping("/todo/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id,
                                        @RequestBody ToDoRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {

        return ResponseEntity.ok(toDoService.updateCard(id, requestDto, userDetails.getUser()));
    }

    // 카드 완료 업데이트
    @PutMapping("/todo/{id}/complete")
    public ResponseEntity<?> completeCard(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {

        return ResponseEntity.ok(toDoService.completeCard(id, userDetails.getUser()));
    }

    // 개시글 비공개 처리
    @PutMapping("/todo/{id}/hidden")
    public ResponseEntity<?> hidden(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException{

        return ResponseEntity.ok(toDoService.hiddenCard(id, userDetails.getUser()));
    }


    // 카드 삭제
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException{

            return ResponseEntity.ok(userDetails.getUser().getUsername() + "님의 "
                    + toDoService.deleteCard(id, userDetails.getUser()) + "번째 글을 삭제 완료했습니다.");
    }


}
