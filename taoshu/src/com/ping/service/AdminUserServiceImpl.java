package com.ping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.mapper.AdminUserMapper;
import com.ping.pojo.Admin;

/**
 * ��̨����Ա�û���ش���
 * @author admin
 *
 */
@Service    //ʹ��ע�����ɸ�ʵ�������
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;
	//������Աע����˺��Ƿ����
	@Override
	public boolean CheckAdminUserName(String admin_username) {
		//����dao����ù���Ա�˺��Ƿ����
		Long checkAdminUserIsExist = adminUserMapper.CheckAdminUserIsExist(admin_username);
		//ʹ����Ԫ���ʽ���Է���long����ֵ�����жϣ������ѯ�������0��˵���˺Ŵ��ڣ����򲻴���
		return checkAdminUserIsExist>0?true:false;
	}
	
	//����Աע��
	@Override
	public int addAdminRegisterInfo(Admin admin) {
		//��ע��Ĺ�����Ϣ���䵽dao�㣬�������ݿ�
		int row=adminUserMapper.addAdminRegisterInfo(admin);	
		return row;
	}

	//����Ա��¼��ͨ���û�������������Ƿ���ڸ��û�
	@Override
	public Admin selectAdminUserByNameAndPassword(Admin admin) {
		Admin isExistAdminUser=adminUserMapper.selectAdminUserByNameAndPassword(admin);
		return isExistAdminUser;
	}

	//�޸����룬ͨ������Ա��id������������޸�
	@Override
	public int updateAdminPassword(int admin_id, String newAdminPassword) {
		//mybatis����ɾ��Ĭ����һ��int���͵�ֵ���أ���ʾӰ�����ݿ��е����������>0��ʾ�ɹ�������ʧ��
		int row=adminUserMapper.updateAdminPassword(admin_id,newAdminPassword);
		return row;
	}

	//���ݹ���Ա��id��ѯ�ù���Ա�ĸ�����Ϣ
	@Override
	public Admin selectAdminInfoById(int admin_id) {
		//����id��ѯ����Ա������Ϣ
		Admin admin=adminUserMapper.selectAdminInfoById(admin_id);
		return admin;
	}

	//�޸ĸ��¹���Ա������Ϣ
	@Override
	public int updateAdminInfo(Admin admin) {
		//�޸ĸ��¹��������Ϣ,row=1���³ɹ���=0����ʧ�ܣ�����Ӱ�����ݿ������
		int row=adminUserMapper.updateAdminInfo(admin);
		return row;
	}

}
