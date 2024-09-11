package com.example.quiz10.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz10.entity.Quiz;

@Repository
public interface QuizDao extends JpaRepository <Quiz, Integer>{

	//PublishedTrue：直接在 published 後面給定 true，所以方法的參數中不用再給 Published
	public boolean existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
			List<Integer> idList, LocalDate now1, LocalDate now2);
	
	//問卷名稱是用模糊比對，所以用 containinga
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String quizName, LocalDate startDate, LocalDate endDate);
}
