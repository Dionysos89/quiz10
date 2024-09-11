package com.example.quiz10.constants;

public enum SelectType {

	SINGLE("Single"),//
	MULTI("Multi"),//
	TEXT("Text");
	
	private String type;

	private SelectType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
