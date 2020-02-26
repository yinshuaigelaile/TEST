package com.ping.mapper;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Admin;

/**
 * 后台管理员用户相关处理，这里使用动态代理技术，自动生成实现类在xml配置一下，和四个注意事项
 * @author admin
 *
 */
public interface AdminUserMapper {

	//通过管理员账号查询该账号是否存在
	public Long CheckAdminUserIsExist(String admin_username);

	//注册管理员账号，
	public int addAdminRegisterInfo(Admin admin);
    
	//管理员登录，通过账号和密码查询是否存在该用户
	public Admin selectAdminUserByNameAndPassword(Admin admin);

	//修改密码，通过管理员的id和新密码进行修改，这时候需要传两个参数，mybatis传多个参数需要使用注解指定名称
	public int updateAdminPassword(@Param("admin_id") int admin_id,@Param("newAdminPassword") String newAdminPassword);

	//根据管理员的id查询该管理员个人信息
	public Admin selectAdminInfoById(int admin_id);
    
	//修改更新管理员个人信息
	public int updateAdminInfo(Admin admin);
}
