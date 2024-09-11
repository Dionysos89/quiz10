package com.example.quiz10.service.ifs;

import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.UserReq;

public interface UserService {
	
	public BasicRes register(UserReq req);
	
	public BasicRes login(UserReq req);
}
