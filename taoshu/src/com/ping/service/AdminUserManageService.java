package com.ping.service;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.User;

/**
 * 后台用户管理
 * @author admin
 *
 */
public interface AdminUserManageService {

	//用户列表分页查询
	AdminPageBean getUserListPageBean(int currentPage, int pageSize,String username);
    //检查该用户名是否存在
	boolean checkUserIsExist(String username);
	//添加用户信息
	int addUserInfo(User user);
	//编辑用户，通过uid查询用户信息进行回显
	User selectUserInfoByUid(int uid);
	//编辑修改用户，通过uid进行修改
	int updateUserInfo(User user);
	//删除用户，通过uid删除用户，批量删除
	int deleteUserInfo(String[] arrayUid);


}
