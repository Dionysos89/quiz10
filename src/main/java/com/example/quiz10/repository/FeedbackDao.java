package com.example.quiz10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz10.entity.Feedback;
import com.example.quiz10.entity.FeedbackId;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, FeedbackId> {

	public boolean existsByQuizIdAndEmail(int quizId, String email);
	
	public List<Feedback> findByQuizIdAndQuIdIn(int quizId, List<Integer> quIdList);
	
}
