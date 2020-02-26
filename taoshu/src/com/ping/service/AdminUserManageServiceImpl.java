package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminUserManageMapper;
import com.ping.pojo.User;

@Service
public class AdminUserManageServiceImpl implements AdminUserManageService {
    
	@Autowired
	private AdminUserManageMapper adminUserManageMapper;
	
	//后台用户管理的用户列表分页查询
	@Override
	public AdminPageBean getUserListPageBean(int currentPage, int pageSize,String username) {
		//主要任何是创建分页对象并封装如下数据,创建分页对象需要确定泛型的类型
		AdminPageBean<User> pageBean=new AdminPageBean<User>();
		//1.查询分页对象属性total总记录数并封装
		int total=adminUserManageMapper.selectUserTotal(username);
		//封装到分页对象
		pageBean.setTotal(total);
		//2.查询分页对象属性rows当前页的的内容并封装
		//分页查询mysql数据库sql语句需要确定limit ？，？两个参数，第一个当前页开始位置（需要计算），第二个参数是每页显示的条数pageSize
		int currentStart=(currentPage-1)*pageSize;//表示当前页开始位置索引
		//调用dao查询当前页内容
		List<User> userList=adminUserManageMapper.selectUserListInfo(currentStart,pageSize,username);
		//封装分页对象当前页内容
		pageBean.setRows(userList);
		//3.返回分页对象
		return pageBean;
	}
  
	//检查该用户名是否存在
	@Override
	public boolean checkUserIsExist(String username) {
		Long checkUserIsExsit=adminUserManageMapper.checkUserIsExist(username);
		return checkUserIsExsit>0?true:false;
	}

	//管理员添加用户信息
	@Override
	public int addUserInfo(User user) {
		//将用户信息传输到dao层将该信息插入到数据库
		int row=adminUserManageMapper.addUserInfo(user);
		return row;
	}
	//编辑用户，通过uid查询用户信息进行回显
	@Override
	public User selectUserInfoByUid(int uid) {
		//调用dao层方法，通过uid查询该用户的信息
		User user=adminUserManageMapper.selectUserInfoByUid(uid);
		return user;
	}
    //编辑修改用户，通过uid进行修改
	@Override
	public int updateUserInfo(User user) {
		//通过uid进行更新用户信息
		//mybatis更新数据库如果成功会返回一个int类型的值，使用row接收如果row=1>0表示更新成功，否则更新失败
		int row=adminUserManageMapper.updateUserInfo(user);
		return row;
	}

	//删除用户，通过uid进行批量删除
	@Override
	public int deleteUserInfo(String[] arrayUid) {
		int row=0;
	  	//遍历数组获取数组中每个uid
		for(int i=0;i<arrayUid.length;i++)
		{
			//得到每个用户的uid，调用dao层进行删除
			String strUid=arrayUid[i];
			//将字符串类型uid转换成int类型
			int uid = Integer.parseInt(strUid);
			//将uid传输到dao层
			row=adminUserManageMapper.deleteUserInfo(uid);
			
		}
		//如果删除成功row其实为1
		return row;
		
	}

	
}
