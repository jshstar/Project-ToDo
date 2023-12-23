package com.sparta.project_todo.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.project_todo.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select c from Comment c join ToDoCard t on c.toDoCard.id = t.id "
		+ "where t.id = :cardId and t.complete =false or (t.hidden = true and t.user.id = :userId)")
	Slice<Comment> findToDoCardComment(Long cardId, Pageable pageable, Long userId);
}
