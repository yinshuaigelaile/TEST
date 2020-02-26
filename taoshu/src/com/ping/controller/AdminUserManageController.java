package com.ping.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.User;
import com.ping.service.AdminUserManageService;

@Controller
public class AdminUserManageController {
	//使用注解自动按照类型进行注入service对象
	@Autowired
	private AdminUserManageService  adminUserManageService;

	/**
	 * 查询用户列表，并分页显示用户信息
	 */
	@RequestMapping("/selectUserListInfo")
	@ResponseBody   //将AdminPageBean分页对象转换成json格式数据返回页面
	//page为easyUI前台提交的当前页，rows为每页显示的条数，变量名是easyUI框架固定
	public AdminPageBean  getSelectUserListInfo(int page,int rows,String username)
	{
		int currentPage=page;//获取前台的当前页
		int pageSize=rows;//每页显示的条数
		//将该数据传输到service层,获得封装好的用户列表分页对象
		AdminPageBean pageBean=adminUserManageService.getUserListPageBean(currentPage,pageSize,username);
		return pageBean;
	}
	
	/**
	 * 管理员添加用户，当输入用户检查该用户名是否存在
	 */
	@RequestMapping("/checkUserIsExist")
	@ResponseBody
	public boolean getUserName(String username)
	{
		//调用service查询该用户名是否存在,返回Boolean类型true或false
		boolean checkUserIsExist=adminUserManageService.checkUserIsExist(username);
		return checkUserIsExist;
	}
	/**
	 * 管理员添加用户
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/addUserInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody  //告诉springmvc视图解析器返回是json数据而不是页面
	public String getAddUserInfo(User user)
	{
		//将封装好的用户信息传输到service层，插入数据库,mybatis会返回一个int值如果row=1>0表示插入成功，否则插入失败
		int row=adminUserManageService.addUserInfo(user);
		if(row>0)
		{
			//添加用户成功，返回json格式字符串交给前台ajax回调函数
			return "{'success':true,'addUserInfo':'添加成功！'}";
		}else
		{
			//添加用户失败，返回json格式字符串交给前台ajax回调函数
			return "{'success':false,'addUserInfo':'添加失败！'}";
		}
	}
	
	/**
	 * 管理员编辑用户，通过前台传输的uid查询该用户信息，并且返回json对象
	 */
	@RequestMapping("/selectUserInfo")
	@ResponseBody   //将user对象转换成json格式的对象
	public User selectUserInfoByUid(int uid)
	{
		//通过uid查询该用户的信息
		User user=adminUserManageService.selectUserInfoByUid(uid);
		//返回用户对象，通过注解@ResponseBody转换成json格式字符串传到前台
		return user;
	}
	/**
	 * 编辑用户，将用户信息更数据库
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/editUserInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditUserInfo(User user)
	{
		//将封装好的user对象传输到service层,通过uid更新用户信息
		int row=adminUserManageService.updateUserInfo(user);
		//mybatis更新数据库如果成功会返回一个int类型的值，使用row接收如果row=1>0表示更新成功，否则更新失败
		if(row>0)
		{
			//更新成功，返回json格式对象字符串给前台提示用户
			return "{'success':true,'editUserInfo':'修改成功！'}";
		}else
		{
			//更新失败
			return "{'success':false,'editUserInfo':'修改失败！'}";
		}
	}
	
	/**
	 * 删除用户信息，将选中的用户从数据库中删除
	 */
	@RequestMapping(value="/deleteUserInfo")
	@ResponseBody
	public String getDeleteUserInfo(String strUid)
	{
		//将字符串按照逗号进行分割
		String[] arrayUid = strUid.split(",");
		//将数组传输到service层
		int row=adminUserManageService.deleteUserInfo(arrayUid);
        //如果row>0表示删除成功，否则失败
		if(row>0)
		{   //书写json格式的对象jQuery不认识单引号,出现多个双引号使用转义字符\，如果是easyUI框架ajax回调就不用这样子，它能识别单引号
			return "{\"success\":true,\"deleteUserInfo\":\"删除成功！\"}";
		}else
		{
			return "{\"success\":false,\"deleteUserInfo\":\"删除失败！\"}";
		}
		
	}
}
