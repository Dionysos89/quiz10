package com.example.quiz10.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeedbackId implements Serializable {

	private int quizId;

	private int quId;

	private String email;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuid() {
		return quId;
	}

	public void setQuid(int quId) {
		this.quId = quId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
