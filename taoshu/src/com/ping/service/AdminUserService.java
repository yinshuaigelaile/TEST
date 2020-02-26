package com.ping.service;

import com.ping.pojo.Admin;

/**
 * 后台管理员用户相关处理
 * @author admin
 *
 */
public interface AdminUserService {
   
	//检查管理员注册的账号是否存在
    public boolean CheckAdminUserName(String admin_username);
    
    //管理员注册
	public int addAdminRegisterInfo(Admin admin);
    
	//管理员登录，通过查询该用户名和密码是否存在该用户 
	public Admin selectAdminUserByNameAndPassword(Admin admin);
    
	//修改密码，通过管理员id和密码
	public int updateAdminPassword(int admin_id, String newAdminPassword);

	//根据管理员的id查询该管理员个人信息
	public Admin selectAdminInfoById(int admin_id);

	//修改更新管理员个人信息
	public int updateAdminInfo(Admin admin);

}
