package com.sparta.project_todo.controller;


import com.sparta.project_todo.dto.GetAllToDoResponseDto;
import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.security.UserDetailsImpl;
import com.sparta.project_todo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

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
        try {
            return new ResponseEntity<>(toDoService.createCard(requestDto, userDetails.getUser()), HttpStatus.CREATED);

        } catch (InputMismatchException e) {
            return new ResponseEntity<>(Map.of("error", "생성할 수 없습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 목록 조회
    @GetMapping("/todo")
    public ResponseEntity<?> getCards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<GetAllToDoResponseDto> responseDto = toDoService.getCards(userDetails);

        if (responseDto.isEmpty()) {
            return new ResponseEntity<>(Map.of("Not index", "목록이 없습니다."), HttpStatus.OK);
        }

        return ResponseEntity.ok(responseDto);
    }

    // 선택된 카드 조회
    @GetMapping("/todo/{id}")
    public ResponseEntity<?> selectGetCard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(toDoService.selectGetCards(id));

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("notFound", "찾으시는 게시글이 없습니다."), HttpStatus.NOT_FOUND);
        }
    }

    // 제목으로 카드목록 조회
    @GetMapping("/todo/title")
    public ResponseEntity<?> getTitleCard(@RequestParam(value = "title") String title,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return new ResponseEntity<>(toDoService.getTitleCards(title, userDetails), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("notFound", "찾으시는 게시글이 없습니다."), HttpStatus.NOT_FOUND);
        }
    }

    // 카드 업데이트
    @PutMapping("/todo/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id,
                                        @RequestBody ToDoRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(toDoService.updateCard(id, requestDto, userDetails.getUser()));

        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // 카드 완료 업데이트
    @PutMapping("/todo/{id}/complete")
    public ResponseEntity<?> completeCard(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(toDoService.completeCard(id, userDetails.getUser()));

        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todo/{id}/hidden")
    public ResponseEntity<?> hidden(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(toDoService.hiddenCard(id, userDetails.getUser()));

        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    // 카드 삭제
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(userDetails.getUser().getUsername() + "님의 " + toDoService.deleteCard(id, userDetails.getUser()) + "글을 삭제 완료했습니다.");

        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }


}