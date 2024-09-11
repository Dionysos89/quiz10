package com.example.quiz10.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QuesId implements Serializable {

	private int quizId;

	private int id;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
