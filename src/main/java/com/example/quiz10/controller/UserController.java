package com.example.quiz10.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz10.service.ifs.UserService;
import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.UserReq;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "user/register")
	public BasicRes register(@Valid @RequestBody UserReq req) {
		return userService.register(req);
	}
	
	@PostMapping(value = "user/login")
	public BasicRes login(@Valid @RequestBody UserReq req) {
		return userService.login(req);
	}
}
