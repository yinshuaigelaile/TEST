package com.lianwei.store.service;

import java.sql.SQLException;
import java.util.Map;

import com.lianwei.store.domain.User;

public interface UserService {

	void userRegister(User user) throws SQLException;

	String findCode(String code) throws SQLException;

	boolean activeUser(String code) throws SQLException;

	User userLogin(User user) throws SQLException;
	
	Map userLogin2(User user) throws SQLException;

}
