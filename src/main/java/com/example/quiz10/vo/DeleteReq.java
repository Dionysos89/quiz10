package com.example.quiz10.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class DeleteReq {

	@NotEmpty(message = "Quiz id list is empty¡I")
	private List<Integer> quesIdList;

	 public List<Integer> getQuesIdList() {
		return quesIdList;
	}

	public void setQuesIdList(List<Integer> quesList) {
		this.quesIdList = quesList;
	}

}
