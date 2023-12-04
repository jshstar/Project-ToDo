package com.sparta.project_todo.controller;

import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.security.UserDetailsImpl;
import com.sparta.project_todo.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping("/todo/{id}/comment")
    public ResponseEntity<?> createComment(@PathVariable(name = "id") Long id,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException{

    System.out.println("들어간드아~!!~!!@!@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return ResponseEntity.ok(commentService.createComment(id, commentRequestDto, userDetails.getUser()));

    }

    // 댓글 업데이트
    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "id") Long id,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {

        return ResponseEntity.ok(commentService.updateComment(id, commentRequestDto, userDetails.getUser()));

    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {

        return ResponseEntity.ok("요청하신" + commentService.deleteComment(id, userDetails.getUser())+ "번째 글이 삭제되었습니다.");
    }


}
