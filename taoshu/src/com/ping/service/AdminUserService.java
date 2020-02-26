package com.ping.service;

import com.ping.pojo.Admin;

/**
 * ��̨����Ա�û���ش���
 * @author admin
 *
 */
public interface AdminUserService {
   
	//������Աע����˺��Ƿ����
    public boolean CheckAdminUserName(String admin_username);
    
    //����Աע��
	public int addAdminRegisterInfo(Admin admin);
    
	//����Ա��¼��ͨ����ѯ���û����������Ƿ���ڸ��û� 
	public Admin selectAdminUserByNameAndPassword(Admin admin);
    
	//�޸����룬ͨ������Աid������
	public int updateAdminPassword(int admin_id, String newAdminPassword);

	//���ݹ���Ա��id��ѯ�ù���Ա������Ϣ
	public Admin selectAdminInfoById(int admin_id);

	//�޸ĸ��¹���Ա������Ϣ
	public int updateAdminInfo(Admin admin);

}
