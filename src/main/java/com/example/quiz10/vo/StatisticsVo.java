package com.example.quiz10.vo;

import java.util.Map;

public class StatisticsVo {

	private int quId;

	private String qu;

	// 				  選項 	  次數
	private Map<String, Integer> optionCountMap;

	public StatisticsVo() {
		super();
	}

	public StatisticsVo(int quId, String qu, Map<String, Integer> optionCountMap) {
		super();
		this.quId = quId;
		this.qu = qu;
		this.optionCountMap = optionCountMap;
	}

	public int getQuId() {
		return quId;
	}

	public String getQu() {
		return qu;
	}

	public Map<String, Integer> getOptionCountMap() {
		return optionCountMap;
	}

	public void setQuId(int quId) {
		this.quId = quId;
	}

	public void setQu(String qu) {
		this.qu = qu;
	}

	public void setOptionCountMap(Map<String, Integer> optionCountMap) {
		this.optionCountMap = optionCountMap;
	}

}
