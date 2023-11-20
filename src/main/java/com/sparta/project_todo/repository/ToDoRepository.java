package com.sparta.project_todo.repository;

import com.sparta.project_todo.entity.ToDoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDoCard, Long> {

    // 완료카드 검색
    List<ToDoCard> findAllByCompleteOrderByCreatedAtDesc(Boolean complete);

    // title을 통한 목록 검색 (유저 로그인 필요)
    @Query( "select t from ToDoCard t   WHERE t.title LIKE %:title% AND (t.complete = false  AND t.hidden = false OR (t.hidden = true AND t.user.id =:id))")
    List<ToDoCard> titleQuery(String title,Long id);

    // (유저 로그인 없이 사용)
    @Query( "select t from ToDoCard t   WHERE t.title LIKE %:title% AND (t.complete = false  AND t.hidden = false) ")
    List<ToDoCard> titleNotUserQuery(String title);


}
