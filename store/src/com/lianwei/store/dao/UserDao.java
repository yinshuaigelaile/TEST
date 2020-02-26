package com.lianwei.store.dao;

import java.sql.SQLException;

import com.lianwei.store.domain.User;

public interface UserDao{

	void register(User user) throws SQLException;

	String findCode(String code) throws SQLException;

	String replaceCode(String replaceValue,String code) throws SQLException;

	User findUserByCode(String code) throws SQLException;

	void update(User user) throws SQLException;

	User userLogin(User user) throws SQLException;

}
