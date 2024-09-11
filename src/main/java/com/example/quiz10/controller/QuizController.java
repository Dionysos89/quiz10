package com.example.quiz10.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz10.service.ifs.QuizService;
import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.CreateUpdateReq;
import com.example.quiz10.vo.DeleteReq;
import com.example.quiz10.vo.FillinReq;
import com.example.quiz10.vo.SearchReq;
import com.example.quiz10.vo.SearchRes;
import com.example.quiz10.vo.StatisticsRes;

@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping(value = "quiz/create")
	public BasicRes create(@Valid @RequestBody CreateUpdateReq req) {
		return quizService.create(req);
	}
	
	@PostMapping(value = "quiz/update")
	public BasicRes update(@Valid @RequestBody CreateUpdateReq req) {
		return quizService.update(req);
	}
	
	@PostMapping(value = "quiz/delete")
	public  BasicRes delete(@Valid @RequestBody DeleteReq req) {
		return quizService.delete(req);
	}
	
	@PostMapping(value = "quiz/search")
	public SearchRes search(@RequestBody SearchReq req) {
		return quizService.search(req);
	}
	
	@PostMapping(value = "quiz/fillin")
	public BasicRes fillin(@Valid @RequestBody FillinReq req) {
		return quizService.fillin(req);
	}
	
	//
	@PostMapping(value = "quiz/statistics")
	public StatisticsRes statistics(@RequestParam int quizId) {
		return quizService.statistics(quizId);
	}
	
	//外部呼叫此 API，必須要使用 JQuery 的方式：quiz/statistics?quiz_id=問卷編號(要有問號)
	@PostMapping(value = "quiz/statistics1")
	public StatisticsRes statistics1(@RequestParam(value = "quiz_id", required = false, defaultValue = "") int quizId) {
		return quizService.statistics(quizId);
	}
}
