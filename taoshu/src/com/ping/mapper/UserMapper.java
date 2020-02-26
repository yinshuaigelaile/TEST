package com.ping.mapper;

import com.ping.pojo.User;

public interface UserMapper {

	//检查用户是否存在
	public Long checkUserIsExist(String username);
	//用户注册
	public int addRegisterInfo(User user);
	//用户登录
	public User selectByUserNameAndPassword(User user);
	
}
