package com.ping.mapper;

import com.ping.pojo.User;

public interface UserMapper {

	//����û��Ƿ����
	public Long checkUserIsExist(String username);
	//�û�ע��
	public int addRegisterInfo(User user);
	//�û���¼
	public User selectByUserNameAndPassword(User user);
	
}
