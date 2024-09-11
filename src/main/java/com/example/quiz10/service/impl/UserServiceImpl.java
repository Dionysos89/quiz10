package com.example.quiz10.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quiz10.constants.ResMessage;
import com.example.quiz10.entity.User;
import com.example.quiz10.repository.UserDao;
import com.example.quiz10.service.ifs.UserService;
import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.UserReq;

@Service
public class UserServiceImpl implements UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserDao userDao;

	@Override
	public BasicRes register(UserReq req) {
		// 檢查帳號是否已被註冊
		if (userDao.existsById(req.getName())) {
			return new BasicRes(ResMessage.USER_NAME_EXISTED.getCode(), ResMessage.USER_NAME_EXISTED.getMessage());
		}
		// 把密碼加密
		String encodePwd = encoder.encode(req.getPwd());
		// 將資料存進資料庫
		userDao.save(new User(req.getName(), encodePwd));
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

	}

	@Override
	public BasicRes login(UserReq req) {
		// 先用帳號去撈資料庫確認是否存在
		Optional<User> op = userDao.findById(req.getName());
		if (op.isEmpty()) {
			return new BasicRes(ResMessage.USER_NAME_NOT_FOUND.getCode(), //
					ResMessage.USER_NAME_NOT_FOUND.getMessage());
		}
		User user = op.get();
		// 比對密碼的密碼
		if (!encoder.matches(req.getPwd(), user.getPwd())) {
			return new BasicRes(ResMessage.PASSWORD_INCONSISTENT.getCode(), //
					ResMessage.PASSWORD_INCONSISTENT.getMessage());
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

}
