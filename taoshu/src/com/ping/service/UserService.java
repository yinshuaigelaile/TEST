package com.ping.service;

import com.ping.pojo.User;

public interface UserService {

	//����û����Ƿ����
	public boolean checkUserIsExist(String username);
	
    //�û�ע��
	public boolean addRegisterInfo(User user);
	
    //�û���¼
	public User selectByUserNameAndPassword(User user);
	
	
	
}
