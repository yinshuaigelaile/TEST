package com.ping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ping.mapper.UserMapper;
import com.ping.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	//检查用户是否存在
	@Override
	public boolean checkUserIsExist(String username) {
		//调用dao层，查询用户是否存在
		Long checkUserIsExist = userMapper.checkUserIsExist(username);
		return checkUserIsExist>0?true:false;
	}
	//用户注册
	
	public boolean addRegisterInfo(User user) {
		//调用dao层，将用户信息插入数据库
		int isnotSuccess = userMapper.addRegisterInfo(user);
		return isnotSuccess>0?true:false;
	}

	//用户登录
	@Override
	public User selectByUserNameAndPassword(User user) {
		//调用dao层查询用户是否存在
	  User isExistUser = userMapper.selectByUserNameAndPassword(user);
		return isExistUser;
	}

	
}
