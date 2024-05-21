package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.User;

public interface UserDao {

	// 회원가입
	int insertUser(User user);
	
	// 로그인 or 개인정보 확인
	User selectUserByEmail(String email);
	User selectUser(int id);
	
	// 전체 유저 확인
	List<User> selectAllUser();
	
	// 회원정보 수정
	int updateUser(User user);
	
	// 회원탈퇴
	int deleteUser(int id);
	
	// 타임 해당 유저 확인
	List<User> selectUsersByTimeId(int timeId);
}
