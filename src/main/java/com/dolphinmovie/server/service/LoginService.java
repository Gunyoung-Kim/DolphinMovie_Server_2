package com.dolphinmovie.server.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.dolphinmovie.server.dao.UserService;
import com.dolphinmovie.server.entity.User;

@Service("loginService")
public class LoginService {
	
	private HashMap<String,User> loginUsers;
	private UserService userService;
	
	private LoginService(UserService userService) {
		loginUsers = new HashMap<>();
		this.userService = userService;
	}
	
	/*
	 *  input user exist in database and has corrected password
	 */
	public void login(User user) {
		loginUsers.put(user.getId(), user);
	}
	
	/*
	 *  return User if input id login, return null if it didn't login
	 */
	
	public User logout(String id) {
		return loginUsers.remove(id);
	}
	
	/*
	 * return User if input id login, return null if it not login
	 */
	
	public User isLogin(String id) {
		if(loginUsers.containsKey(id)) {
			return loginUsers.get(id);
		} else {
			return null;
		}
	}
	
	/*
	 *  return current number of current login users
	 */
	
	public int getLoginUsersNumber() {
		return loginUsers.size();
	}
}
