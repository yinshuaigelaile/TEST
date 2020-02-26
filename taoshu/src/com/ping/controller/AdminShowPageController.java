package com.ping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *     ��ʾ�����̳Ǻ�̨��¼��ע��ҳ�棬��ҳ
 */
@Controller
public class AdminShowPageController {

	
	/**
	 * ��ʾ��̨��¼����
	 */
	@RequestMapping("/adminLogin")
	public String showAdminLoginPage()
	{
		return "admin/adminLogin";
	}
	
	/**
	 * ��ʾ��̨ע�����
	 */
	@RequestMapping("/adminRegister")
	public String showAdminRegisterPage()
	{
		return "admin/adminRegister";
	}
	
	/**
	 * ��ʾ��̨��ҳ
	 */
	@RequestMapping("/adminIndex")
	public String showAdminIndexPage()
	{
		return "admin/adminIndex";
	}
	
	/**
	 * ��ʾ��ҳ�����޸ĸ�����Ϣҳ��edit_adminInfo.jsp
	 */
	@RequestMapping("/edit_adminInfo")
	public String showEdit_adminInfo()
	{
		return "admin/edit_adminInfo";
	}
	
	/**
	 * ��ʾ�û��б�ҳ��
	 */
	@RequestMapping("/adminUser_list")
	public String showAdminUser_list()
	{
		return "admin/adminUser_list";
	}
	
	/**
	 * ��ʾ�����б�ҳ��
	 */
	@RequestMapping("/adminCategory_list")
	public String showAdminCategory_list()
	{
		return "admin/adminCategory_list";
	}
	
	/**
	 * ��ʾ��Ʒ�б�ҳ��
	 */
	@RequestMapping("/adminProduct_list")
	public String showAdminProduct_list()
	{
		return "admin/adminProduct_list";
	}
	
	
	/**
	 * ��ʾ�����б�ҳ��
	 */
	@RequestMapping("/adminOrder_list")
	public String showAdminOrder_list()
	{
		return "admin/adminOrder_list";
	}
	
}
