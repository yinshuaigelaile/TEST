package com.ping.service;

import com.ping.pojo.User;

public interface UserService {

	//检查用户名是否存在
	public boolean checkUserIsExist(String username);
	
    //用户注册
	public boolean addRegisterInfo(User user);
	
    //用户登录
	public User selectByUserNameAndPassword(User user);
	
	
	
}
