package com.example.quiz10.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
public class User {

	@NotBlank(message = "Name is Empty¡I")
	@Id
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Password is Empty¡I")
	@Column(name = "password")
	private String pwd;

	public User() {
		super();
	}

	public User(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public String getPwd() {
		return pwd;
	}

}
