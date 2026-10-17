package com.tap1.dao;

import java.util.List;

import com.tap1.model.User;

public interface UserDao {
	void addUser(User user);
	User getUserByName(String username);
	User getUserByEmail(String email);
	User getUser(int userId);
	void updateUser(User user);
	void deleteUser(int userId);
	List<User> getAllUser();

}
