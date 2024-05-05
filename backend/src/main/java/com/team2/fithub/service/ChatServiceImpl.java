package com.team2.fithub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.ChatDao;
import com.team2.fithub.model.dto.Chat;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatDao chatDao;
	
	@Autowired
    public ChatServiceImpl(ChatDao chatDao) {
        this.chatDao = chatDao;
    }
	
	@Override
	@Transactional
	public int addChat(Chat chat) {
		return chatDao.insertChat(chat);
	}

	@Override
	public List<Chat> findChat(int mentorId, int userId) {
		return chatDao.selectChat(mentorId, userId);
	}

	@Override
	public Map<Integer, List<Chat>> findChatByUser(int userId) {
		List<Chat> chats = chatDao.selectChatByUser(userId);
		Map<Integer, List<Chat>> userChatMap = new HashMap<>();
		for(Chat chat : chats) {
			int mentorId = chat.getMentorId();
            List<Chat> innerChats = userChatMap.getOrDefault(mentorId, new ArrayList<>());
            innerChats.add(chat);
            userChatMap.put(mentorId, innerChats);
		}
		return userChatMap;
	}

	@Override
	public Map<Integer, List<Chat>> findChatByMentor(int mentorId) {
		List<Chat> chats = chatDao.selectChatByMentor(mentorId);
		Map<Integer, List<Chat>> mentorChatMap = new HashMap<>();
		for(Chat chat : chats) {
			int userId = chat.getUserId();
            List<Chat> innerChats = mentorChatMap.getOrDefault(userId, new ArrayList<>());
            innerChats.add(chat);
            mentorChatMap.put(userId, innerChats);
		}
		return mentorChatMap;
	}

	@Override
	@Transactional
	public int remvoeChat(int id) {
		return chatDao.deleteChat(id);
	}

}
