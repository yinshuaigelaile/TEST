package com.lianwei.store.dao;

import java.util.List;

import com.lianwei.store.domain.User;

public interface AdminUserDao {

	List<User> findAllUser(String grade) throws Exception;

	List<User> findAllUser() throws Exception;
}
