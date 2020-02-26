package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.User;

/**
 * ��̨�û�����
 * @author admin
 *
 */
public interface AdminUserManageMapper {

	//�û��б���ѯ�û����е��ܼ�¼��(�û�������),������������û�������Ҫ�����û�������ģ����ѯ
	public int selectUserTotal(@Param("username") String username);
    //��ѯ��ǰҳ�û��б���Ϣ
	public List<User> selectUserListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("username") String username);
	//�����û����Ƿ����
	public Long checkUserIsExist(String username);
	//����Ա����û���Ϣ
	public int addUserInfo(User user);
	//����Ա�༭�û���ͨ��uid��ѯ�û���Ϣ���л���
	public User selectUserInfoByUid(int uid);
	//����Ա�༭�޸��û���ͨ��uid�����޸�
	public int updateUserInfo(User user);
	//ɾ���û���ͨ��uid����ɾ��
	public int deleteUserInfo(int uid);
}
