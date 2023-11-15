package com.sparta.project_todo.service;

import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.dto.ToDoResponseDto;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository boardRepository;
    public ToDoService(ToDoRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 작성기능
    public ToDoResponseDto createCard(ToDoRequestDto boardRequestDto)
    {
        // RequestDto = Entity
        ToDoCard board = new ToDoCard(boardRequestDto);
        //DB 저장
        ToDoCard saveBoard = boardRepository.save(board);

        return new ToDoResponseDto(saveBoard);
    }



    // 게시글 목록 조회
    public List<ToDoResponseDto> getCards(){
        //DB 조회
        return boardRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(ToDoResponseDto::new).toList();
    }

    // 선택한 게시글 조회 기능
    public ToDoResponseDto selectGetCards(Long bNum){
        ToDoCard board = findBoard(bNum);
        return new ToDoResponseDto(board);
    }

    // 선택한 게시글 수정 기능
    @Transactional
    public ToDoResponseDto updateCard(Long bNum, ToDoRequestDto boardRequestDto)
    {
        ToDoCard board = findBoard(bNum);
        board.update(boardRequestDto);
        return new ToDoResponseDto(board);
    }

    // pw 확인 메서드
    public boolean pwCheck(Long bNum, String pw)
    {
        ToDoCard checkBoard = findBoard(bNum);
        // PW 같은지 체크
        return checkBoard.getPw().equals(pw);
    }



    // 선택한 게시글 삭제 기능
    public Long deleteBoard(Long bNum)
    {
        ToDoCard board = findBoard(bNum);
        boardRepository.delete(board);
        return bNum;
    }


    // 예외처리와 동시에 원하는 데이터값 출력
    // TODO 궁금한 사항 Controller에서 주석처리를 진행했지만?
    //  findById 자체가 에러처리 구문이 필수로 사용되는데
    //  이런 상황에서 다른 구조가 마땅히 생각이 안나서 일단 사용했습니다.
    private ToDoCard findBoard(Long bNum){
        return boardRepository.findById(bNum).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );


    }


}
