package com.signinner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.signinner.dao.User;
import com.signinner.dao.UserDAO;


@Service("UserService")
public class UserService {
	@Autowired
	private UserDAO userDao;
	
	@Transactional
	public int register(String username, String password, int identity){
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setIdentity(identity);
		user.setSchool(1);
		user.setMajor(1);
		int a=userDao.save(user);
		return a;
	}

	public int setUserInfo(String userKey, String name) {
		// TODO Auto-generated method stub
		return userDao.setUserInfo(Integer.valueOf(userKey),name);
	}
	
	public int luru(String userKey, String person_id) {
		// TODO Auto-generated method stub
		return userDao.setPersonId(Integer.valueOf(userKey),person_id);
	}

	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.login(username, password);
	}
	
}
