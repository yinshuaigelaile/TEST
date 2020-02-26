package com.lianwei.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianwei.store.domain.User;
import com.lianwei.store.service.AdminUserService;
import com.lianwei.store.service.serviceImpl.AdminUserServiceImpl;
import com.lianwei.store.web.base.BaseServlet;

@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends BaseServlet {
		//查询各种类型用户
		public String findAllUser(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
			//接收参数grade值
			String grade = req.getParameter("grade");
			AdminUserService adminUserService = new AdminUserServiceImpl();
			List<User> list = null;
			if (null==grade||"".equals(grade)) {
				list = adminUserService.findAllUser();
			}else {
				list = adminUserService.findAllUser(grade);
			}
			//将存储的值返回到页面
			req.setAttribute("list", list);
			//转发到用户页面/admin/user/list.jsp
			return "/admin/user/list.jsp";
		}
}
