package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Mentor;

public interface MentorDao {
	
	// 회원가입
	int insertMentor(Mentor mentor);
	
	// 로그인 or 개인정보 확인
	Mentor selectMentorByEmail(String email);
	Mentor selectMentor(int id);
	
	// 전체 멘토 확인
	List<Mentor> selectAllMentor();
	
	// 멘토 회원정보 수정
	int updateMentor(Mentor user);
	
	// 회원탈퇴
	int deleteMentor(int id);
}
