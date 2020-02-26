package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminUserManageMapper;
import com.ping.pojo.User;

@Service
public class AdminUserManageServiceImpl implements AdminUserManageService {
    
	@Autowired
	private AdminUserManageMapper adminUserManageMapper;
	
	//��̨�û�������û��б��ҳ��ѯ
	@Override
	public AdminPageBean getUserListPageBean(int currentPage, int pageSize,String username) {
		//��Ҫ�κ��Ǵ�����ҳ���󲢷�װ��������,������ҳ������Ҫȷ�����͵�����
		AdminPageBean<User> pageBean=new AdminPageBean<User>();
		//1.��ѯ��ҳ��������total�ܼ�¼������װ
		int total=adminUserManageMapper.selectUserTotal(username);
		//��װ����ҳ����
		pageBean.setTotal(total);
		//2.��ѯ��ҳ��������rows��ǰҳ�ĵ����ݲ���װ
		//��ҳ��ѯmysql���ݿ�sql�����Ҫȷ��limit ������������������һ����ǰҳ��ʼλ�ã���Ҫ���㣩���ڶ���������ÿҳ��ʾ������pageSize
		int currentStart=(currentPage-1)*pageSize;//��ʾ��ǰҳ��ʼλ������
		//����dao��ѯ��ǰҳ����
		List<User> userList=adminUserManageMapper.selectUserListInfo(currentStart,pageSize,username);
		//��װ��ҳ����ǰҳ����
		pageBean.setRows(userList);
		//3.���ط�ҳ����
		return pageBean;
	}
  
	//�����û����Ƿ����
	@Override
	public boolean checkUserIsExist(String username) {
		Long checkUserIsExsit=adminUserManageMapper.checkUserIsExist(username);
		return checkUserIsExsit>0?true:false;
	}

	//����Ա����û���Ϣ
	@Override
	public int addUserInfo(User user) {
		//���û���Ϣ���䵽dao�㽫����Ϣ���뵽���ݿ�
		int row=adminUserManageMapper.addUserInfo(user);
		return row;
	}
	//�༭�û���ͨ��uid��ѯ�û���Ϣ���л���
	@Override
	public User selectUserInfoByUid(int uid) {
		//����dao�㷽����ͨ��uid��ѯ���û�����Ϣ
		User user=adminUserManageMapper.selectUserInfoByUid(uid);
		return user;
	}
    //�༭�޸��û���ͨ��uid�����޸�
	@Override
	public int updateUserInfo(User user) {
		//ͨ��uid���и����û���Ϣ
		//mybatis�������ݿ�����ɹ��᷵��һ��int���͵�ֵ��ʹ��row�������row=1>0��ʾ���³ɹ����������ʧ��
		int row=adminUserManageMapper.updateUserInfo(user);
		return row;
	}

	//ɾ���û���ͨ��uid��������ɾ��
	@Override
	public int deleteUserInfo(String[] arrayUid) {
		int row=0;
	  	//���������ȡ������ÿ��uid
		for(int i=0;i<arrayUid.length;i++)
		{
			//�õ�ÿ���û���uid������dao�����ɾ��
			String strUid=arrayUid[i];
			//���ַ�������uidת����int����
			int uid = Integer.parseInt(strUid);
			//��uid���䵽dao��
			row=adminUserManageMapper.deleteUserInfo(uid);
			
		}
		//���ɾ���ɹ�row��ʵΪ1
		return row;
		
	}

	
}
