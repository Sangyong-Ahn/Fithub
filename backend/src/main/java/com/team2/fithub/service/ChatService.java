package com.team2.fithub.service;

import java.util.List;
import java.util.Map;

import com.team2.fithub.model.dto.Chat;

public interface ChatService {
	
	int addChat(Chat chat);
	
	List<Chat> findChat(int mentorId, int userId);
	
	Map<Integer, List<Chat>> findChatByUser(int userId);
	
	Map<Integer, List<Chat>> findChatByMentor(int mentorId);
	
	int remvoeChat(int id);
}
