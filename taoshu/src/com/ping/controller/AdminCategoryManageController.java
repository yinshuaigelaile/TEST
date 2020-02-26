package com.ping.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;
import com.ping.pojo.User;
import com.ping.service.AdminCategoryManageService;

/**
 * 后台分类管理，包括增删改查
 */
@Controller
public class AdminCategoryManageController {
	
	@Autowired
	private AdminCategoryManageService adminCategoryManageService;
	/**
	 * 查询分类列表，通过输入分类名称模糊查询并分页显示分类信息
	 */
	//通过分类名称模糊查询所有分类信息,并且进行分页显示（如果没有输入查询所有分类信息，例如管理员点击左侧分类列表）
	@RequestMapping("/selectCategoryListInfo")
	@ResponseBody   //将AdminPageBean分页对象转换成json格式数据返回页面，easyUI数据表格自动填充数据
	//page为easyUI前台提交的当前页，rows为每页显示的条数，变量名是easyUI框架固定
	public AdminPageBean  getSelectCategoryListInfo(int page,int rows,String cname)
	{
		int currentPage=page;//获取前台的当前页
		int pageSize=rows;//每页显示的条数
		//将该数据传输到service层,获得封装好的分类列表分页对象
		AdminPageBean pageBean=adminCategoryManageService.getCategoryListPageBean(currentPage,pageSize,cname);
		return pageBean;
	}
	
	/**
	 * 管理员添加分类，当输入分类名称检查该名称是否存在
	 */
	@RequestMapping("/checkCategoryIsExist")
	@ResponseBody
	public boolean getCategoryName(String cname)
	{
		//调用service查询该分类名称是否存在,返回Boolean类型true或false
		boolean checkCategoryIsExist=adminCategoryManageService.checkCategoryIsExist(cname);
		return checkCategoryIsExist;
	}
	
	/**
	 * 管理员添加分类
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/addCategoryInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody  //告诉springmvc视图解析器返回是json数据而不是页面
	public String getAddCategoryInfo(Category category)
	{
		//将封装好的分类信息传输到service层，插入数据库,mybatis会返回一个int值如果row=1>0表示插入成功，否则插入失败
		int row=adminCategoryManageService.addCategoryInfo(category);
		if(row>0)
		{
			//添加用户成功，返回json格式字符串交给前台ajax回调函数
			return "{'success':true,'addCategoryInfo':'添加成功！'}";
		}else
		{
			//添加用户失败，返回json格式字符串交给前台ajax回调函数
			return "{'success':false,'addCategoryInfo':'添加失败！'}";
		}
	}
	
	/**
	 * 管理员编辑分类，通过前台传输的cid查询该分类信息，并且返回json对象
	 */
	@RequestMapping("/selectCategoryInfo")
	@ResponseBody   //将user对象转换成json格式的对象
	public Category selectCategoryInfoByUid(int cid)
	{
		//通过cid查询该分类的信息
		Category category=adminCategoryManageService.selectCategoryInfoByCid(cid);
		//返回分类对象，通过注解@ResponseBody转换成json格式字符串传到前台
		return category;
	}
	
	/**
	 * 编辑分类，将分类信息更数据库
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/editCategoryInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditCategoryInfo(Category category)
	{
		//将封装好的category对象传输到service层,通过cid更新用户信息
		int row=adminCategoryManageService.updateCategoryInfo(category);
		//mybatis更新数据库如果成功会返回一个int类型的值，使用row接收如果row=1>0表示更新成功，否则更新失败
		if(row>0)
		{
			//更新成功，返回json格式对象字符串给前台提示用户
			return "{'success':true,'editCategoryInfo':'修改成功！'}";
		}else
		{
			//更新失败
			return "{'success':false,'editCategoryInfo':'修改失败！'}";
		}
	}
	
	/**
	 * 删除分类信息，将选中的分类从数据库中删除
	 */
	@RequestMapping(value="/deleteCategoryInfo")
	@ResponseBody
	public String getDeleteUserInfo(String strCid)
	{
		//将字符串按照逗号进行分割
		String[] arrayCid = strCid.split(",");
		//将数组传输到service层
		int row=adminCategoryManageService.deleteCategoryInfo(arrayCid);
        //如果row>0表示删除成功，否则失败
		if(row>0)
		{   //书写json格式的对象jQuery不认识单引号,出现多个双引号使用转义字符\，如果是easyUI框架ajax回调就不用这样子，它能识别单引号
			return "{\"success\":true,\"deleteCategoryInfo\":\"删除成功！\"}";
		}else
		{
			return "{\"success\":false,\"deleteCategoryInfo\":\"删除失败！\"}";
		}
		
	}
}
