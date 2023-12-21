package com.sparta.project_todo.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sparta.project_todo.todocard.entity.ToDoCard;
import com.sparta.project_todo.todocard.repository.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "완료처리된 카드 삭제 Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

	private final ToDoRepository toDoRepository;

	// 매 1시간마다 90일이 지난 카드가 있는지 검사
	// @Scheduled(cron = "0 0 0/1 * *")
	// public void expireDeleteCardCheck(){
	// 	log.info("90일이 지난 카드 삭제실행");
	// 	LocalDateTime currentLocalDateTime = LocalDateTime.now();
	// 	LocalDateTime pastLocalDateTime = currentLocalDateTime.minusDays(90);
	// 	List<ToDoCard> expireCardList = toDoRepository.deleteExpireCard(pastLocalDateTime);
	// }

	// 테스트 코드 5분마다 실행 생성된지 5분 이상된 카드 삭제
	@Scheduled(cron = "0 0/5 * * * *")
	public void expireDeleteCardCheck(){
		log.info("1분이 지난 카드 삭제 실행");
		LocalDateTime currentLocalDateTime = LocalDateTime.now();
		LocalDateTime pastLocalDateTime = currentLocalDateTime.minusMinutes(5);
		toDoRepository.deleteExpireCard(pastLocalDateTime);
	}



}
