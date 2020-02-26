package com.lianwei.store.web.servlet;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;

import com.lianwei.store.domain.User;
import com.lianwei.store.service.UserService;
import com.lianwei.store.service.serviceImpl.UserServiceImpl;
import com.lianwei.store.utils.MailUtils;
import com.lianwei.store.utils.MyBeanUtils;
import com.lianwei.store.utils.UUIDUtils;
import com.lianwei.store.web.base.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	
	public String registerUI(HttpServletRequest request,HttpServletResponse response) {
		
		return "/jsp/register.jsp";	
	}
	
	public String loginUI(HttpServletRequest request,HttpServletResponse response) {
		
		return "/jsp/login.jsp";	
	}
	public String userRegister(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//接收表单参数   request.getParameter()
		//request.getParameterMap()
		Map<String, String[]> map = request.getParameterMap();
		UserService userService = new UserServiceImpl();
		User user = new User();
//		DateConverter dt = new DateConverter();
//		dt.setPattern("yyyy-mm-dd");
//		ConvertUtils.convert(dt, java.util.Date.class);
//		BeanUtils.populate(user, map);
		MyBeanUtils.populate(user, map);
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		System.out.println(user); 
		//调用业务层注册方法
		
		try {
			userService.userRegister(user);
			//注册成功，向用户邮箱发送信息，跳转提示页面
			//发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功,请激活");
			return "/jsp/info.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", "用户注册失败，请重新注册!");
		}
		return "/jsp/info.jsp";	
	}
	
	/**登录的流程
	 * 步骤
	 * 1.输入用户名和密码，用户名可以采用ajax进行校验，校验通过后才可以执行登录操作
	 * 2.登录时，根据用户名和密码到数据库中查询出对应的user，接下来判断是否为空
	 * 3.为空代表用户密码错误，抛出异常 密码错误login.jsp    request。setAttribute("msg",e.getMessage())   
	 * 4.不为空，但是state为0，抛出异常未激活，请激活   login.jsp   request。setAttribute("msg",e.getMessage())
	 * 5.如果都满足，就将获取的user返回到servlet，将user作为参数传到客户端，index。jsp   request。setAttribute("loginUser",user)   response.setRendact("/store/index.jsp")
	 * @param request 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String userLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserService userService = new UserServiceImpl();
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		User user2 = null;
		try {
			user2 = userService.userLogin(user);
			request.getSession().setAttribute("loginUser", user2);
			response.sendRedirect("/store/index.jsp");
			return null;
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			return "/jsp/login.jsp";
		}
		
	}
	
	public String loginOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		response.sendRedirect("/store/index.jsp");
		return null;
	}
	
	public String loginOut2(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser")!=null) {
			session.removeAttribute("loginUser");
			response.sendRedirect("/store/index.jsp");
		}
		return null;
	}
	/**
	 * 激活用户
	 * 步骤
	 * 1.传过来参数code，将code作为参数到数据库查出对应的user
	 * 2.为空返回到info页面，提醒激活失败
	 * 3.不为空时就将当前的激活码设置为null，state状态设置为1，也就是激活状态，然后将数据库里的内容更改，并跳转到登录，提醒激活成功
	 * @param request
	 * @param response
	 * @return
	 */
	public String activeUser(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		UserService userService = new UserServiceImpl();
		boolean flag = false;
		try {
			flag = userService.activeUser(code);
			if(flag==true) {
				request.setAttribute("msg", "激活成功，请登录");
				return "/jsp/login.jsp";
			}else {
				return "/jsp/info.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 第二张登录方法，无非就是引进一个变量 map.get("value")
	 */
	public String userLogin2(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserService userService = new UserServiceImpl();
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		User user2 = null;
		try {
			Map<String, Object> map = userService.userLogin2(user);
			if ("userNotExist".equals(map.get("value"))) {
				request.setAttribute("msg", "用户密码错误");
				return "/jsp/login.jsp";
			}else if ("userNotActive".equals("value")) {
				request.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}else {
				user2 = (User) map.get("user");
				request.getSession().setAttribute("loginUser", user2);
				response.sendRedirect("/store/index.jsp");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 点击链接网址，激活用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//此方法激活用户不佳
	public String active(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		UserService userService = new UserServiceImpl();
		try {
			String value = userService.findCode(code);
			if ("1".equals(value)) {
				System.out.println("激活成功");
				request.setAttribute("msg", "激活成功");
			}else {
				request.setAttribute("msg", "激活失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/jsp/info.jsp";
	}
	
	
	/**
	  * 关于获取form的三种方式
	  * 通过获取form的所有map数据
	 * map数据包含所有键值对的形式，键是String，值是一个String数组
	  * 所有可以通过获取键来获取所对应的值，
	 */
	//方法1
	public void getFormValue1(Map<String, String[]> map) {
		//遍历map中的数据
				Set<String> keySet = map.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String str = iterator.next();
					System.out.println(str);
					String[] strs = map.get(str);
					for (String string : strs) {
						System.out.println(string);
					}
					System.out.println();
				}
	}
	//方法2
	public void getFormValue2(Map<String, String[]> map) {
		
		Set<Entry<String, String[]>> entrySet = map.entrySet();
		for (Map.Entry<String, String[]> entry : entrySet) {
			System.out.println(entry.getKey());
			for (String value : entry.getValue()) {
				System.out.println(value);
			}
			System.out.println();
		}
	}
	
}
