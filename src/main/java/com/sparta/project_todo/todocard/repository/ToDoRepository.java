package com.sparta.project_todo.todocard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.project_todo.todocard.entity.ToDoCard;

public interface ToDoRepository extends JpaRepository<ToDoCard, Long> {

    // 완료카드 검색
    List<ToDoCard> findAllByCompleteOrderByCreatedAtDesc(Boolean complete);

    // title을 통한 목록 검색 (유저 로그인 필요)
    @Query( "select t from ToDoCard t   WHERE t.title LIKE %:title% AND (t.complete = false  AND t.hidden = false OR (t.hidden = true AND t.user.id =:id))")
    List<ToDoCard> titleQuery(String title,Long id);

    // (유저 로그인 없이 사용)
    @Query( "select t from ToDoCard t   WHERE t.title LIKE %:title% AND (t.complete = false  AND t.hidden = false) ")
    List<ToDoCard> titleNotUserQuery(String title);

    // 완료가 false인 카드인 동시에 User id와 히든이 True인 게시글의 User의 Id가 같으면 목록에 포함된 데이터를 가졍온다.
    // @Query( "select t from ToDoCard t WHERE (t.complete = false  AND t.hidden = false) OR (t.hidden = true AND t.user.id =:id)")
    // Page<ToDoCard> findPageCard (Pageable pageable, boolean complete, Long userId);

    @Query("select t from ToDoCard t WHERE (t.complete = false AND t.hidden = false) OR (t.hidden = true AND t.user.id = :id)")
    Page<ToDoCard> findPageCard(Pageable pageable, @Param("id") Long userId);


}
