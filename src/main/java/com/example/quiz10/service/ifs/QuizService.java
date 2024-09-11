package com.example.quiz10.service.ifs;

import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.CreateUpdateReq;
import com.example.quiz10.vo.DeleteReq;
import com.example.quiz10.vo.FillinReq;
import com.example.quiz10.vo.SearchReq;
import com.example.quiz10.vo.SearchRes;
import com.example.quiz10.vo.StatisticsRes;

public interface QuizService {

	public BasicRes create(CreateUpdateReq req);
	
	public BasicRes update(CreateUpdateReq req);
	
	public  BasicRes delete(DeleteReq req);
	
	public SearchRes search(SearchReq req);
	
	public BasicRes fillin(FillinReq req);
	
	public StatisticsRes statistics(int quizId);
}
