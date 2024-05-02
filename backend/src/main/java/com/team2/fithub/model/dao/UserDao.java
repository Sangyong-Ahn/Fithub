package com.team2.fithub.model.dao;

import com.team2.fithub.model.dto.User;

public interface UserDao {

	// 회원가입
	int insertUser(User user);
	
	// 로그인 or 개인정보 확인
	User selectUser(String email);
	
	// 회원정보 수정
	int updateUser(User user);
	
	// 회원탈퇴
	int deleteUser(String email);
	
}
