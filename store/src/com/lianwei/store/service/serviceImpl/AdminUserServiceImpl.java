package com.lianwei.store.service.serviceImpl;

import java.util.List;

import com.lianwei.store.dao.AdminUserDao;
import com.lianwei.store.dao.daoImpl.AdminUserDaoImpl;
import com.lianwei.store.domain.User;
import com.lianwei.store.service.AdminUserService;

public class AdminUserServiceImpl implements AdminUserService {

	@Override
	public List<User> findAllUser(String grade) throws Exception {
		//查询到所有的用户信息
		AdminUserDao adminUserDao = new AdminUserDaoImpl();
		return adminUserDao.findAllUser(grade);
	}

	@Override
	public List<User> findAllUser() throws Exception {
		AdminUserDao adminUserDao = new AdminUserDaoImpl();
		return adminUserDao.findAllUser();
	}

}
