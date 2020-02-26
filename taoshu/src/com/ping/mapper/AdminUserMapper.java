package com.ping.mapper;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Admin;

/**
 * ��̨����Ա�û���ش�������ʹ�ö�̬���������Զ�����ʵ������xml����һ�£����ĸ�ע������
 * @author admin
 *
 */
public interface AdminUserMapper {

	//ͨ������Ա�˺Ų�ѯ���˺��Ƿ����
	public Long CheckAdminUserIsExist(String admin_username);

	//ע�����Ա�˺ţ�
	public int addAdminRegisterInfo(Admin admin);
    
	//����Ա��¼��ͨ���˺ź������ѯ�Ƿ���ڸ��û�
	public Admin selectAdminUserByNameAndPassword(Admin admin);

	//�޸����룬ͨ������Ա��id������������޸ģ���ʱ����Ҫ������������mybatis�����������Ҫʹ��ע��ָ������
	public int updateAdminPassword(@Param("admin_id") int admin_id,@Param("newAdminPassword") String newAdminPassword);

	//���ݹ���Ա��id��ѯ�ù���Ա������Ϣ
	public Admin selectAdminInfoById(int admin_id);
    
	//�޸ĸ��¹���Ա������Ϣ
	public int updateAdminInfo(Admin admin);
}
