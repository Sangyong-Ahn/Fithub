package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Chat;

public interface ChatDao {
	
	int insertChat(Chat chat);
	
	List<Chat> selectChat(int mentorId, int userId);
	
	List<Chat> selectChatByUser(int userId);
	
	List<Chat> selectChatByMentor(int mentorId);
	int deleteChat(int id);
}
