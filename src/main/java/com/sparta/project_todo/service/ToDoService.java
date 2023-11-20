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

    private final ToDoRepository toDoRepository; // ToDoDB 정보 가져오기

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

        List<ToDoCard> toDoCardsList = toDoRepository.findAllByCompleteOrderByCreatedAtDesc(false); // 완료처리 안된 카드 목록 가져오기
        List<ToDoCard> getToDoCardList = new ArrayList<>(); // 원하는 정보를 담을 공간 생성

        String currentUsername = user != null ? user.getUsername() : ""; // 유저 로그인 확인(유저가 작성한 카드 유무 확인 )
        String toDoCardUsername; // 카드목록의 유저이름 정보를 담을 변수

        for (ToDoCard todoCard : toDoCardsList) {
            toDoCardUsername = todoCard.getUser().getUsername(); // column당 유저의 이름 담기

            if (todoCard.isHidden()) { // 카드 정보에 비공개 처리 유무 확인

                if (toDoCardUsername.equals(currentUsername)) // 카드의 유저 이름과 조회하는 유저의 이름 일치 확인
                    getToDoCardList.add(todoCard); // 일치한다면 조회 목록에 추가

            } else getToDoCardList.add(todoCard); // 비공개 처리가 안돼 있는 정보 추가
        }

        if(getToDoCardList.isEmpty())
            throw new NullPointerException("카드 목록이 없습니다.");

        return getToDoCardList.stream().map(GetAllToDoResponseDto::new).toList(); // 결과 리턴

    }

    //선택한 카드 조회 기능
    public SelectToDoResponseDto selectGetCards(Long id){

        ToDoCard card = findCard(id); // 번호에 따른 카드정보 탐색 및 영속성 저장

        return new SelectToDoResponseDto(card);
    }

    // 제목으로 검색
    public List<GetAllToDoResponseDto> getTitleCards(String title, UserDetailsImpl user) {

        List<GetAllToDoResponseDto> titleList;

        if(user != null) {
            titleList = toDoRepository.titleQuery(title, user.getUser().getId()) // Query 문을 통한 유저 정보 가져오기
                    .stream().map(GetAllToDoResponseDto::new).toList();
        } else {
            titleList = toDoRepository.titleNotUserQuery(title) // Query 문을 통한 로그인 안한 유저 정보 가져오기
                    .stream().map(GetAllToDoResponseDto::new).toList();
        }

        if(titleList.isEmpty())
            throw new NullPointerException("찾으시는 Card가 없습니다.");

        return titleList;
    }

    // 선택한 카드 수정 기능
    @Transactional
    public SelectToDoResponseDto updateCard(Long id, ToDoRequestDto cardRequestDto, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id); // 수정할 카드정보 탐색 및 영속성 저장

        matchUsername(card, user);  // 카드의 유저 이름과 수정할 유저의 이름이 같은지 확인
        card.update(cardRequestDto); // 같다면 수정

        return new SelectToDoResponseDto(card); // 수정 정보 반환

    }

    // 선택한 카드 완료 기능
    @Transactional
    public CompleteToDoResponseDto completeCard(Long id, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id); // 완료 처리할 카드정보 탐색 및 영속성 저장

        matchUsername(card, user); // 카드의 유저 이름과 완료처리할 유저의 이름이 같은지 확인
        card.complete(true); // 같다면 완료처리

        return new CompleteToDoResponseDto(card); // 완료 정보 반환
    }

    @Transactional
    public HiddenToDoResponseDto hiddenCard(Long id, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id); // 비공개 처리할 카드정보 탐색 및 영속성 저장

        matchUsername(card, user);  // 카드의 유저 이름과 완료처리할 유저의 이름이 같은지 확인
        card.hiddenFlag(true); // 비공개 처리

        return new HiddenToDoResponseDto(card);
    }

    // 선택한 게시글 삭제 기능
    public Long deleteCard(Long id, User user) throws IllegalAccessException {
        ToDoCard card = findCard(id); // 삭제 처리할 카드 탐색 및 영속성 저장

        matchUsername(card, user);  // 카드의 유저 이름과 삭제처리할 유저의 이름이 같은지 확인
        toDoRepository.delete(card); // 카드삭제

        return id; // 아이디 반환
    }

    // 카드 탐색
    public ToDoCard findCard(Long id) {
        return toDoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }

    // 카드의 유저이름과 접근한 유저의 이름이 같은지 확인
    public void matchUsername(ToDoCard card, User user) throws IllegalAccessException{
        if(!card.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalAccessException("접근할 수 없는 카드입니다.");
    }
}
