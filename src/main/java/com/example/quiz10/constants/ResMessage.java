package com.example.quiz10.constants;

public enum ResMessage {

	SUCCESS(200, "Success¡I"),//
	DATE_ERROR(400, "Start date cannot be later than end date¡I"),//
	OPTIONS_ERROR(400, "Options cannot be null or empty¡I"),
	QUIZ_NOT_FOUND(404, "Quiz not found¡I"),//
	QUIZ_ID_NOT_MATCH(400, "Quiz id not match¡I"),//
	QUIZ_IN_PROGRESS(400, "Quiz in Progress¡I"),//
	QUIZ_ID_OR_EMAIL_INCONSISTENT(400, "Quiz id or email inconsistent¡I"),//
	EMAIL_DUPLICATE(400, "email duplicate¡I"),
	CANNOT_FILLIN_QUIZ(400, "cannot fillin quiz¡I"),//
	FILLIN_INCOMPLETE(400, "Fillin incomplete¡I"),//
	FILLIN_IS_NECESSARY(400, "Fillin in necessary¡I"),//
	QUID_MISMATCH(400, "Quid mismatch¡I"),//
	SINGLE_CHOICE_QUES(400, "Single choice ques¡I"),//
	OPTION_ANSWER_MISMATCH(400, "Option answer mismatch¡I"),//
	USER_NAME_EXISTED(400, "User name existed¡I"), //
	USER_NAME_NOT_FOUND(400, "User name not found¡I"), //
	PASSWORD_INCONSISTENT(400, "Password inconsistent¡I");
	
	private int code;

	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
