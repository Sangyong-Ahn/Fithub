package com.team2.fithub.service;

import com.team2.fithub.model.dto.User;

public interface UserService {
	
	int addUser(User user);
	
	User findUser(String email);
	
	int modifyUser(User user);
	
	int removeUser(String email);
	
}
