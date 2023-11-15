package com.sparta.project_todo.controller;


import com.sparta.project_todo.dto.BoardRequestDto;
import com.sparta.project_todo.dto.BoardResponseDto;
import com.sparta.project_todo.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService){this.boardService = boardService;}

    // 게시글 작성
    @PostMapping("/boards")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto requestDto) {
        try{
            return new ResponseEntity<>(boardService.createBoard(requestDto),HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("잘못된 양식입니다.",HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 목록 조회
    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards(){
        return boardService.getBoards();
    }

    // 선택된 게시글 조회
    @GetMapping("/boards/{bNum}")
    public ResponseEntity<?> selectGetBoard(@PathVariable Long bNum){
        try
        {
            return new ResponseEntity<>(boardService.selectGetBoard(bNum),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("찾으시는 게시글이 없습니다.",HttpStatus.NOT_FOUND);
        }

    }

    // 게시글 업데이트
    @PutMapping("/boards/{bNum}")
    public ResponseEntity<?> updateBoard(@PathVariable Long bNum, @RequestBody BoardRequestDto requestDto)
    {
        return boardService.pwCheck(bNum, requestDto.getPw()) ?
                new ResponseEntity<>(boardService.updateBoard(bNum,requestDto),HttpStatus.OK):
                new ResponseEntity<>("비밀번호가 틀렸습니다.",HttpStatus.UNAUTHORIZED);
    }

    // 게시글 삭제
    @DeleteMapping("/boards/{bNum}/{pw}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long bNum, @PathVariable String pw)
    {
        return boardService.pwCheck(bNum, pw) ?
                new ResponseEntity<>("작성하신 "+ boardService.deleteBoard(bNum) + " 번째 게시글이 정상적으로 삭제됐습니다.", HttpStatus.OK):
                new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED);
    }


}