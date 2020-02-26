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
	//����û��Ƿ����
	@Override
	public boolean checkUserIsExist(String username) {
		//����dao�㣬��ѯ�û��Ƿ����
		Long checkUserIsExist = userMapper.checkUserIsExist(username);
		return checkUserIsExist>0?true:false;
	}
	//�û�ע��
	
	public boolean addRegisterInfo(User user) {
		//����dao�㣬���û���Ϣ�������ݿ�
		int isnotSuccess = userMapper.addRegisterInfo(user);
		return isnotSuccess>0?true:false;
	}

	//�û���¼
	@Override
	public User selectByUserNameAndPassword(User user) {
		//����dao���ѯ�û��Ƿ����
	  User isExistUser = userMapper.selectByUserNameAndPassword(user);
		return isExistUser;
	}

	
}
