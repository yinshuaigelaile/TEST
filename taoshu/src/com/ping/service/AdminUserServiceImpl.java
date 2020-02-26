package com.ping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.mapper.AdminUserMapper;
import com.ping.pojo.Admin;

/**
 * 后台管理员用户相关处理
 * @author admin
 *
 */
@Service    //使用注解生成该实现类对象
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;
	//检查管理员注册的账号是否存在
	@Override
	public boolean CheckAdminUserName(String admin_username) {
		//调用dao层检查该管理员账号是否存在
		Long checkAdminUserIsExist = adminUserMapper.CheckAdminUserIsExist(admin_username);
		//使用三元表达式，对返回long类型值进行判断，如果查询结果大于0就说该账号存在，否则不存在
		return checkAdminUserIsExist>0?true:false;
	}
	
	//管理员注册
	@Override
	public int addAdminRegisterInfo(Admin admin) {
		//将注册的管理信息传输到dao层，插入数据库
		int row=adminUserMapper.addAdminRegisterInfo(admin);	
		return row;
	}

	//管理员登录，通过用户名和密码存在是否存在该用户
	@Override
	public Admin selectAdminUserByNameAndPassword(Admin admin) {
		Admin isExistAdminUser=adminUserMapper.selectAdminUserByNameAndPassword(admin);
		return isExistAdminUser;
	}

	//修改密码，通过管理员的id和新密码进行修改
	@Override
	public int updateAdminPassword(int admin_id, String newAdminPassword) {
		//mybatis，增删改默认有一个int类型的值返回，表示影响数据库中的行数，如果>0表示成功，否则失败
		int row=adminUserMapper.updateAdminPassword(admin_id,newAdminPassword);
		return row;
	}

	//根据管理员的id查询该管理员的个人信息
	@Override
	public Admin selectAdminInfoById(int admin_id) {
		//根据id查询管理员个人信息
		Admin admin=adminUserMapper.selectAdminInfoById(admin_id);
		return admin;
	}

	//修改更新管理员个人信息
	@Override
	public int updateAdminInfo(Admin admin) {
		//修改更新管理个人信息,row=1更新成功，=0更新失败，代表影响数据库的行数
		int row=adminUserMapper.updateAdminInfo(admin);
		return row;
	}

}
