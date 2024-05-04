package com.team2.fithub.service;

import java.util.List;

import com.team2.fithub.model.dto.User;

public interface UserService {
	
	int addUser(User user);
	
	User findUserByEmail(String email);
	User findUser(int id);
	
	List<User> findAllUser();
	
	int modifyUser(User user);
	
	int removeUser(int id);
	
}
