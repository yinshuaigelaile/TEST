package com.ping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.AdminPageBean;
import com.ping.service.AdminCategoryManageService;
import com.ping.service.AdminOrderManageService;

/**
 * 后台订单管理
 * @author admin
 *
 */
@Controller
public class AdminOrderManageController {

	@Autowired
	private AdminOrderManageService adminOrderManageService;
	/**
	 * 查询订单列表，还可以根据用户输入订单号进行查询
	 */
	//如果用户输入订单号就查询当前页该订单信息，否则查询所有订单当前页信息
	@RequestMapping("/selectOrderListInfo")
	@ResponseBody   //将AdminPageBean分页对象转换成json格式数据返回页面，easyUI数据表格自动填充数据
	//page为easyUI前台提交的当前页，rows为每页显示的条数，变量名是easyUI框架固定
	public AdminPageBean  getSelectOrderListInfo(int page,int rows,String oid)
	{
		int currentPage=page;//获取前台的当前页，默认前台提交第一页
		int pageSize=rows;//每页显示的条数，默认显示10条
		//将该数据传输到service层,获得封装好的订单列表分页对象
		AdminPageBean pageBean=adminOrderManageService.getOrderListPageBean(currentPage,pageSize,oid);
		return pageBean;
	}
	
	/**
	 * 显示订单详情，列出订单里面的所有订单项
	 */
	@RequestMapping("/selectOrderItemListInfo")
	@ResponseBody   //将AdminPageBean分页对象转换成json格式数据返回页面，easyUI数据表格自动填充数据
	//page为easyUI前台提交的当前页，rows为每页显示的条数，变量名是easyUI框架固定
	public AdminPageBean  getSelectOrderItemListInfo(int page,int rows,String oid)
	{
		int currentPage=page;//获取前台的当前页，默认前台提交第一页
		int pageSize=rows;//每页显示的条数，默认显示10条
		//将该数据传输到service层,获得封装好的订单列表分页对象
		AdminPageBean pageBean=adminOrderManageService.getOrderItemListPageBean(currentPage,pageSize,oid);
		return pageBean;
	
		
	}
}
