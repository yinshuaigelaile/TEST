package com.ping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *     显示易淘商城后台登录，注册页面，首页
 */
@Controller
public class AdminShowPageController {

	
	/**
	 * 显示后台登录界面
	 */
	@RequestMapping("/adminLogin")
	public String showAdminLoginPage()
	{
		return "admin/adminLogin";
	}
	
	/**
	 * 显示后台注册界面
	 */
	@RequestMapping("/adminRegister")
	public String showAdminRegisterPage()
	{
		return "admin/adminRegister";
	}
	
	/**
	 * 显示后台首页
	 */
	@RequestMapping("/adminIndex")
	public String showAdminIndexPage()
	{
		return "admin/adminIndex";
	}
	
	/**
	 * 显示首页里面修改个人信息页面edit_adminInfo.jsp
	 */
	@RequestMapping("/edit_adminInfo")
	public String showEdit_adminInfo()
	{
		return "admin/edit_adminInfo";
	}
	
	/**
	 * 显示用户列表页面
	 */
	@RequestMapping("/adminUser_list")
	public String showAdminUser_list()
	{
		return "admin/adminUser_list";
	}
	
	/**
	 * 显示分类列表页面
	 */
	@RequestMapping("/adminCategory_list")
	public String showAdminCategory_list()
	{
		return "admin/adminCategory_list";
	}
	
	/**
	 * 显示商品列表页面
	 */
	@RequestMapping("/adminProduct_list")
	public String showAdminProduct_list()
	{
		return "admin/adminProduct_list";
	}
	
	
	/**
	 * 显示订单列表页面
	 */
	@RequestMapping("/adminOrder_list")
	public String showAdminOrder_list()
	{
		return "admin/adminOrder_list";
	}
	
}
