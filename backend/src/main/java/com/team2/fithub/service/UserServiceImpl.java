package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.UserDao;
import com.team2.fithub.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

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
	
	@Override
	public List<User> findAllUser() {
		return userDao.selectAllUser();
	}

	@Override
	public User findUser(int id) {
		return userDao.selectUser(id);
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userDao.selectUserByEmail(email);
	}

	@Transactional
	@Override
	public int modifyUser(User user) {
		return userDao.updateUser(user);
	}

	@Transactional
	@Override
	public int removeUser(int id) {
		return userDao.deleteUser(id);
	}
}
