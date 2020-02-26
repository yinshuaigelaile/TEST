package com.ping.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.pojo.User;
import com.ping.service.UserService;

/**
 * 1.用户注册ajax同步判断用户是否存在
 * 2.用户注册
 * 3.用户登录
 * 4.退出登录
 */
@Controller
public class CheckUserNameController {

	@Autowired
	private UserService userService;

	/**
	 * 1.检查用户名是否存在
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	public boolean getUserInfo(String username) {
		boolean checkUserIsExist = userService.checkUserIsExist(username);
		return checkUserIsExist;
	}

	/**
	 * 2.用户注册
	 */
	@RequestMapping("/registerUser")
	public String getRegisterInfo(User user, String validatecode, HttpServletRequest request, Model model) {
		// 检验验证码是否正确
		// 获取生成放在session中的验证码
		HttpSession session = request.getSession();
		String checkcode = (String) session.getAttribute("code");
		// 比较验证码是否一致
		if (StringUtils.isEmpty(validatecode)) {
			model.addAttribute("registerinfo", "验证码不能为空！");
			return "register";
		} else if (!checkcode.equals(validatecode)) {
			// 如果两次验证码不一致，输出提示信息
			model.addAttribute("registerinfo", "您输入的验证码不正确,注意区分大小写！");
			return "register";
		} else {
			// 输入的验证码正确，将用户信息插入到数据库
			boolean isNotSuccess = userService.addRegisterInfo(user);
			if (isNotSuccess) {
				//System.out.println("注册成功！");
				return "registerSuccess";
			} else {
				//System.out.println("注册失败！");
				return "registerFail";
			}

		}

	}

	/**
	 * 3.用户登录
	 * 
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/loginUser")
	public String login(User user1, HttpServletRequest request, HttpServletResponse response, String validatecode,
			Model model) throws UnsupportedEncodingException {
		// 获取controller生成验证码
		HttpSession session = request.getSession();
		String checkImg = (String) session.getAttribute("code");
		// 判断输入的验证码是否为空
		if (StringUtils.isEmpty(validatecode)) {
			// 如果是空，返回到登录页面，输出错误提示信息
			model.addAttribute("logininfo", "验证码不能为空！");
			return "login";
		}
		// 判断输入验证码和生成的验证码是否一致
		else if (!checkImg.equals(validatecode)) {
			// 如果不一致就返回登录页面输出错误提示信息
			model.addAttribute("logininfo", "输入的验证码不正确,注意区分大小写!");
			return "login";
		} else {
			// 执行到这里，满足验证码输入不为空并且相等，接下来效验用户名和密码
			// 通过用户名和密码查询数据库是否存在该用户
			User isExistUser = userService.selectByUserNameAndPassword(user1);
			if (isExistUser != null) {
				// 登录成功就将用户存在到session中，以便用来判断用户是否登录
				session.setAttribute("user", isExistUser);
				//登录成功还有做两件事情，判断用户是否选择记住用户名和密码，是否选择自动登录
				
				//1. 判断用户是否选择记住用户名和密码
				String parameter = request.getParameter("remember_me");
				if (parameter != null) {
					// 由于cookie是不能存储中文，对象中文的用户名要进行编码
					String encodeUserName = URLEncoder.encode(isExistUser.getUsername(), "UTF-8");
					// 1.用户选择记住用户名和密码，创建cookie将用户名和密码存储到cookie中
					Cookie cookie_username = new Cookie("username1", encodeUserName);
					Cookie cookie_password = new Cookie("password1", isExistUser.getPassword());
					// 2.设置cookie的存储时间,以秒为单位，设置为一个小时
					cookie_username.setMaxAge(60 * 60);
					cookie_password.setMaxAge(60 * 60);
					// 3.设置cookie的携带路径
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.将cookie添加到客户端浏览器上
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);

				} else {
					/**
					 * 清除cookie信息，如果这次用户没有选择记住用户名和密码，应该清除上一次存储cookie中用户名和密码
					 */
					// 如果用户没有选择记住用户名和密码，将原来存储在cookie中的用户名和密码清除
					// 1.怎么清楚cookie，就是创建和原来名称一样cookie，存储null,将原来覆盖，并且设置存储时间为0
					Cookie cookie_username = new Cookie("username1", null);
					Cookie cookie_password = new Cookie("password1", null);
					// 2.设置存储时间为0
					cookie_username.setMaxAge(0);
					cookie_password.setMaxAge(0);
					// 3.设置cookie的携带路径
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.将该cookie添加到客户端
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);
				}
				
				// 判断用户是否选择自动登录
				String autoLogin = request.getParameter("autologin");
				if (autoLogin != null) {
					// 说明用户选择自动登录，将用户的用户名和密码存储到cookie中
					// 由于cookie是不能存储中文，对象中文的用户名要进行编码
					String encodeUserName = URLEncoder.encode(isExistUser.getUsername(), "UTF-8");
					// 1.用户选择记住用户名和密码，创建cookie将用户名和密码存储到cookie中
					Cookie cookie_username = new Cookie("username2", encodeUserName);
					Cookie cookie_password = new Cookie("password2", isExistUser.getPassword());
					// 2.设置cookie的存储时间,以秒为单位，设置为一个小时
					cookie_username.setMaxAge(60 * 60);
					cookie_password.setMaxAge(60 * 60);
					// 3.设置cookie的携带路径
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.将cookie添加到客户端浏览器上
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);

				} else {
					/**
					 * 清除cookie信息,这次没有选择自动登录，应该清空上一次选择自动登录的cookie信息
					 */
					// 如果用户没有选择记住用户名和密码，将原来存储在cookie中的用户名和密码清除
					// 1.怎么清楚cookie，就是创建和原来名称一样cookie，存储null,将原来覆盖，并且设置存储时间为0
					Cookie cookie_username = new Cookie("username2", null);
					Cookie cookie_password = new Cookie("password2", null);
					
					// 2.设置存储时间为0
					cookie_username.setMaxAge(0);
					cookie_password.setMaxAge(0);
					// 3.设置cookie的携带路径
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.将该cookie添加到客户端
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);
					
				}
				
				//登录成功重定向到首页
				return "redirect:index";
			} else {
				model.addAttribute("logininfo", "用户名或者密码错误，请从新输入！");
				return "login";
			}
		}

	}

	/**
	 * 4.退出登录，就是将存储在session中的用户删除掉，相当于注销用户
	 */
	@RequestMapping("/deleteUser")
	public String deleteUserInfo(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		//清除cookie中用户自动登录的用户名和密码
		/**
		 * 清除cookie信息,自动登录，在选择退出登录应该清除该cookie信息，
		 * 不然每次访问项目任何东西都走拦截器都会取cookie中用户名和密码，
		 * 每次都登录，每次存储用户到session，使得退出登录点击没有效果
		 */
		// 如果用户没有选择记住用户名和密码，将原来存储在cookie中的用户名和密码清除
		// 1.怎么清楚cookie，就是创建和原来名称一样cookie，存储null,将原来覆盖，并且设置存储时间为0
		Cookie cookie_username = new Cookie("username2", null);
		Cookie cookie_password = new Cookie("password2", null);
		//2.设置存储时间为0
		cookie_username.setMaxAge(0);
		cookie_password.setMaxAge(0);
		//3.设置cookie的携带路径
		cookie_username.setPath(request.getContextPath());
		cookie_password.setPath(request.getContextPath());
		//4.将该cookie添加到客户端
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		/**
		 * 记住用户名和密码就不要清除cookie，不然点击退出登录，显示登录页面就无法从cookie中获取用户名和密码显示到控件上
		 */
		return "redirect:login";
	}
	
}
