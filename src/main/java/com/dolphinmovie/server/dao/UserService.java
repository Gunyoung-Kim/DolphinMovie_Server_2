package com.dolphinmovie.server.dao;

import java.util.List;

import com.dolphinmovie.server.entity.User;

public interface UserService {
	public List<User> findAll();
	public User findByAddress(String address);
	public void saveUser(User user);
	public void removeUser(User user);
	public Long findIdByAddress(String address);
	public User findById(Long id) throws Exception;
}
