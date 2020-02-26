package com.ping.service;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.User;

/**
 * ��̨�û�����
 * @author admin
 *
 */
public interface AdminUserManageService {

	//�û��б��ҳ��ѯ
	AdminPageBean getUserListPageBean(int currentPage, int pageSize,String username);
    //�����û����Ƿ����
	boolean checkUserIsExist(String username);
	//����û���Ϣ
	int addUserInfo(User user);
	//�༭�û���ͨ��uid��ѯ�û���Ϣ���л���
	User selectUserInfoByUid(int uid);
	//�༭�޸��û���ͨ��uid�����޸�
	int updateUserInfo(User user);
	//ɾ���û���ͨ��uidɾ���û�������ɾ��
	int deleteUserInfo(String[] arrayUid);


}
