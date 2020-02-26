package com.lianwei.store.service;

import java.util.List;

import com.lianwei.store.domain.User;

public interface AdminUserService {

	List<User> findAllUser(String grade) throws Exception;

	List<User> findAllUser() throws Exception;

}
