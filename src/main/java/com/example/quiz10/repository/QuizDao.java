package com.example.quiz10.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz10.entity.Quiz;

@Repository
public interface QuizDao extends JpaRepository <Quiz, Integer>{

	//PublishedTrue�G�����b published �᭱���w true�A�ҥH��k���ѼƤ����ΦA�� Published
	public boolean existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
			List<Integer> idList, LocalDate now1, LocalDate now2);
	
	//�ݨ��W�٬O�μҽk���A�ҥH�� containinga
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String quizName, LocalDate startDate, LocalDate endDate);
}