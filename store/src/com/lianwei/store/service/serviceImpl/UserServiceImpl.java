package com.lianwei.store.service.serviceImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.lianwei.store.dao.UserDao;
import com.lianwei.store.dao.daoImpl.UserDaoImpl;
import com.lianwei.store.domain.User;
import com.lianwei.store.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao  userDao = new UserDaoImpl();
	@Override
	public void userRegister(User user) throws SQLException{
		//实现注册功能
		userDao.register(user);
	}

	@Override
	public String findCode(String code) throws SQLException {
		String replaceValue = "1";
		String codeValue = userDao.findCode(code);
		String value = "0";
		if("0".equals(codeValue)) {
			value = userDao.replaceCode(replaceValue,code);
		}
		
		return value;
	}

	@Override
	public boolean activeUser(String code) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findUserByCode(code);
		if (null!=user) {
			user.setCode(null);
			user.setState(1);
			userDao.update(user);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public User userLogin(User user) throws SQLException{
		UserDao userDao = new UserDaoImpl();
		User user2=userDao.userLogin(user);
		if (null == user2) {
			throw new RuntimeException("登录用户密码错误");
			
		}else if(user2.getState()==0){
			throw new RuntimeException("登录用户未激活，请先激活");
		}else {
			return user2;
		}
	}
	
	/**
	 * 第二种登录，传变量值
	 */
	@Override
	public Map userLogin2(User user) throws SQLException{
		UserDao userDao = new UserDaoImpl();
		User user2=userDao.userLogin(user);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == user2) {
			map.put("value", "userNotExist");
		}else if(user2.getState()==0){
			map.put("value", "userNotActive");
		}else {
			map.put("value", "userExist");
			map.put("user", user2);
		}
		return map;
//		if (null == user2) {
//			throw new RuntimeException("登录用户密码错误");
//			
//		}else if(user2.getState()==0){
//			throw new RuntimeException("登录用户未激活，请先激活");
//		}else {
//			return user2;
//		}
	}

}
