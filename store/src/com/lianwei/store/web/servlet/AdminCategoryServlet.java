package com.lianwei.store.web.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianwei.store.domain.Category;
import com.lianwei.store.service.AdminCategoryService;
import com.lianwei.store.service.serviceImpl.AdminCategoryServiceImpl;
import com.lianwei.store.utils.MyBeanUtils;
import com.lianwei.store.utils.UUIDUtils;
import com.lianwei.store.web.base.BaseServlet;


@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	//findAllCats 查找所有分类信息
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		//查找所有分类信息
		AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl();
		List<Category> list = adminCategoryService.findAllCats();
		//将其放入request
		req.setAttribute("list", list);
		//转发到页面对应的页面"/admin/category/list.jsp"
		return "/admin/category/list.jsp";
	}
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		return "/admin/category/add.jsp";
	}
	
	public String addCategory(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		String cname = req.getParameter("cname");
		AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl();
		Category category = new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		adminCategoryService.addCategory(category);
		resp.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	
	public String editCategoryUI(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		String cid = req.getParameter("cid");
		AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl();
		Category category = adminCategoryService.findCatByCid(cid);
		req.setAttribute("category",category);
		return "admin/category/edit.jsp";
	}
	
	public String editCategory(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		Map<String, String[]> map = req.getParameterMap();
		Category category = new Category();
		AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl();
		MyBeanUtils.populate(category, map);
		adminCategoryService.editCategory(category);
		resp.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	
	
	public String delCategory(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		String cid = req.getParameter("cid");
		AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl();
		Category category = adminCategoryService.findCatByCid(cid);
		adminCategoryService.delCategory(category);
		resp.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
		return null;
	}	
}
