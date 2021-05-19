package com.dolphinmovie.server.dao;

import java.util.List;

import com.dolphinmovie.server.entity.User;

public interface UserService {
	public List<User> findAll();
	public User saveUser(User user);
	public void removeUser(User user);
	public User findById(String id);
	public User checkPassword(String id, String password);
}
