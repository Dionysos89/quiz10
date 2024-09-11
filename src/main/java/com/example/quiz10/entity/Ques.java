package com.example.quiz10.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ques")
@IdClass(value = QuesId.class)
public class Ques {

	@Min(value = 0, message = "Quiz Id must be negative¡I")
	@Id
	@Column(name = "quiz_id")
	private int quizId;

	@Min(value = 1, message = "Ques Id must be greater than 0¡I")
	@Id
	@Column(name = "id")
	private int id;

	@NotBlank(message = "Question cannot be null or empty¡I")
	@Column(name = "qu")
	private String qu;

	@NotBlank(message = "Type cannot be null or empty¡I")
	@Column(name = "type")
	private String type;

	@Column(name = "necessary")
	private boolean necessary;

	@Column(name = "options")
	private String options;

	public Ques() {
		super();
	}

	public Ques(int quizId, int id, String qu, String type, boolean necessary, String options) {
		super();
		this.quizId = quizId;
		this.id = id;
		this.qu = qu;
		this.type = type;
		this.necessary = necessary;
		this.options = options;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getId() {
		return id;
	}

	public String getQu() {
		return qu;
	}

	public String getType() {
		return type;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public String getOptions() {
		return options;
	}

}
