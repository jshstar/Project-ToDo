package com.sparta.project_todo.service;

import com.sparta.project_todo.dto.*;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.repository.ToDoRepository;
import com.sparta.project_todo.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    // 할일카드 작성기능
    public ToDoResponseDto createCard(ToDoRequestDto cardRequestDto, User user) {
        // RequestDto = Entity
        ToDoCard card = new ToDoCard(cardRequestDto, user);
        //DB 저장
        ToDoCard saveBoard = toDoRepository.save(card);
        return new ToDoResponseDto(saveBoard);
    }


    // 카드 목록 조회
    public List<GetAllToDoResponseDto> getCards(UserDetailsImpl user) {
        List<ToDoCard> toDoCardsList = toDoRepository.findAllByCompleteOrderByCreatedAtDesc(false);
        List<ToDoCard> getToDoCardList = new ArrayList<>();
        String currentUsername = user!=null? user.getUsername(): "";
        String toDoCardUsername;
        for (ToDoCard todoCard : toDoCardsList) {
            toDoCardUsername = todoCard.getUser().getUsername();
            if(todoCard.isHidden()) {
                if(toDoCardUsername.equals(currentUsername))
                    getToDoCardList.add(todoCard);
            }
            else  getToDoCardList.add(todoCard);
        }
        return getToDoCardList.stream().map(GetAllToDoResponseDto::new).toList();

    }

    //선택한 카드 조회 기능
    public SelectToDoResponseDto selectGetCards(Long id){
        ToDoCard card = findCard(id);
        return new SelectToDoResponseDto(card);
    }

    // 제목으로 검색
    public List<GetAllToDoResponseDto> getTitleCards(String title, User user){

        return toDoRepository.titleQuery(title,user.getId())
                .stream().map(GetAllToDoResponseDto::new).toList();
    }

    // 선택한 카드 수정 기능
    @Transactional
    public SelectToDoResponseDto updateCard(Long id, ToDoRequestDto cardRequestDto, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id);
        if (card.getUser().getUsername().equals(user.getUsername())) {
            card.update(cardRequestDto);
            return new SelectToDoResponseDto(card);
        } else throw new IllegalAccessException("접근 실패");
    }

    // 선택한 카드 완료 기능
    @Transactional
    public CompleteToDoResponseDto completeCard(Long id, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id);
        if (card.getUser().getUsername().equals(user.getUsername())) {
            card.complete(true);
            return new CompleteToDoResponseDto(card);
        } else throw new IllegalAccessException("유저 접근 실패");

    }

    @Transactional
    public HiddenToDoResponseDto hiddenCard(Long id, User user) throws IllegalAccessException{
        ToDoCard card = findCard(id);
        if(card.getUser().getUsername().equals(user.getUsername()))
        {
            card.hiddenFlag(true);
            return new HiddenToDoResponseDto(card);
        }
        else throw new IllegalAccessException("유저 접근 실패");
    }

    // 선택한 게시글 삭제 기능
    public Long deleteCard(Long id, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id);
        if (card.getUser().getUsername().equals(user.getUsername())) {
            toDoRepository.delete(card);
            return id;
        } else throw new IllegalAccessException("유저 접근 실패");
    }

    public ToDoCard findCard(Long id) {
        return toDoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
