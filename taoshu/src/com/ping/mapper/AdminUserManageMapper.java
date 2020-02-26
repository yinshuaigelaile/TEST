package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.User;

/**
 * 后台用户管理
 * @author admin
 *
 */
public interface AdminUserManageMapper {

	//用户列表，查询用户表中的总记录数(用户的总数),如果存在输入用户名还需要根据用户名进行模糊查询
	public int selectUserTotal(@Param("username") String username);
    //查询当前页用户列表信息
	public List<User> selectUserListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("username") String username);
	//检查该用户名是否存在
	public Long checkUserIsExist(String username);
	//管理员添加用户信息
	public int addUserInfo(User user);
	//管理员编辑用户，通过uid查询用户信息进行回显
	public User selectUserInfoByUid(int uid);
	//管理员编辑修改用户，通过uid进行修改
	public int updateUserInfo(User user);
	//删除用户，通过uid进行删除
	public int deleteUserInfo(int uid);
}
