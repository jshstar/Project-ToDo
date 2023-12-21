package com.sparta.project_todo.comment.repository;

import com.sparta.project_todo.comment.entity.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// 수정해야됨
	@Query("select c from Comment c join ToDoCard t on c.toDoCard.id = :cardId where t.complete =false and(t.hidden = true and t.user.id = :userId)")
	Slice<Comment> findToDoCardComment(Long cardId, Pageable pageable, Long userId);
}
