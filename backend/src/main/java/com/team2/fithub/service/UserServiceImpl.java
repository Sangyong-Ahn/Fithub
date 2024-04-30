package com.team2.fithub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.UserDao;
import com.team2.fithub.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Autowired
	public void setUserRepo(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserRepo() {
		return this.userDao;
	}
	
	@Override
	@Transactional
	public int addUser(User user) {
		return userDao.insertUser(user);
	}

}
