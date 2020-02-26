package com.ping.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.pojo.Admin;
import com.ping.service.AdminUserService;

@Controller
public class AdminUserController {
    
	//使用注解自动按照类型注入service对象
	@Autowired
	private AdminUserService adminUserService;
	/**
	 * 通过ajax检查后台注册的管理员账号是否存在
	 */
	@RequestMapping("/checkAdminUserName")
	@ResponseBody   //使用该注解将返回Boolean类型值转换成json格式
	public boolean CheckAdminUserName(String admin_username)
	{
		//将管理员注册的账号，传输到service层，查询数据库是否存在该账号
		boolean checkAdminUserName = adminUserService.CheckAdminUserName(admin_username);
		return checkAdminUserName;
	}
	
	/**
	 * 管理员注册账号
	 * 当访问该路径，springmvc会自动按类型封装到对象的属性里面
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/adminUserRegister",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getAdminRegisterInfo(Admin admin,String checkImg,HttpServletRequest request)
	{
		//获取用户注册信息，首先判断用户输入的验证码是否正确，如果正确再将该数据传输到service层，否则提示用户验证码不正确
		//对比用户输入的验证码和存储在session中生成的验证码是否一致
		HttpSession session = request.getSession();
		//获取session中存储生成的验证码
		String checkCode = (String) session.getAttribute("code");
		//比较用户输入的验证码和生成验证码是否一致,比较字符串是否相等,checkImg为封装前台用户输入的验证码
		if(!checkCode.equals(checkImg))
		{
			//验证码不正确，提示用户输入的验证码不正确，这里相当于设置一道门
			//将要提示的信息，写成json格式的字符串，返回给前台
			return "{'checkCode':true,'checkCodeInfo':'验证码不正确，注意区分大小写！'}";//return会结束方法，下面代码不会执行
			
		}
		
		//能过来说明验证码正确，将注册信息传输到service层
		int row=adminUserService.addAdminRegisterInfo(admin);
		//判断row是否大于0，如果是就说明插入成功就是注册成功，否则注册失败
		if(row>0)
		{
			//注册成功
			return "{'success':true,'registerInfo':'注册成功！'}";
		}else
		{
			//注册失败
			return "{'success':false,'registerInfo':'注册失败！'}";
		}
		
	}
	
	/**
	 * 管理员登录
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/adminUserLogin",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody   //该注解告诉视图解释器返回时json，而不是jsp页面
	public String getAdminLoginInfo(Admin admin,String checkImg,HttpServletRequest request,HttpServletResponse response)
	{
		//1.判断验证码是否正确，获取用户输入的验证码和生成的验证码进行比较，生成的验证码是存储session中
		//获取session中存储生成的验证码
		HttpSession session = request.getSession();
		String checkCode = (String) session.getAttribute("code");
		//判断输入的验证码和生成的验证码是否正确
		if(!checkCode.equals(checkImg))
		{
			//如果不相等，就提示用户验证码输入不正确！,return 返回json格式的字符串
			return "{'checkCode':true,'checkCodeInfo':'验证码不正确，注意区分大小写！'}";
		}
		//2.判断管理员用户名和密码是否正确,通过用户名和密码查询是否存在该用户
		Admin isExistAdminUser=adminUserService.selectAdminUserByNameAndPassword(admin);
		//查询结果isExistAdminUser是否为空，如果不为空说明存在该用户，否则不存在
		if(isExistAdminUser!=null)
		{
			//登录成功，提示用户登录成功,前台提示设置跳转到首页
			//4.将管理用户信息存储到session中
			session.setAttribute("adminUser", isExistAdminUser);
			//3.判断用户是否选择记住用户和密码
            String remember = request.getParameter("remember_me");
            if(remember!=null)
            {
            	//说明用户选择记住用户名和密码，需要将用户名和密码存储到cookie添加到客户端
            	//3.1创建cookie,将管理员的账号和密码存储到cookie中
            	//存储账号到cookie
            	Cookie cookie_adminName=new Cookie("adminUserName",isExistAdminUser.getAdmin_name() );
            	//存储密码到cookie
            	Cookie cookie_adminPassword=new Cookie("adminUserPassword",isExistAdminUser.getAdmin_password());
            	//3.2设置cookie存储时间,以秒为单位，60*60为一个小时
            	cookie_adminName.setMaxAge(60*60);
            	cookie_adminPassword.setMaxAge(60*60);
            	//3.3设置cookie携带路径,设置为访问该工程的路径
            	cookie_adminName.setPath(request.getContextPath());
            	cookie_adminPassword.setPath(request.getContextPath());
            	//3.4将设置好的cookie对象添加到客户端
            	response.addCookie(cookie_adminName);
            	response.addCookie(cookie_adminPassword);
            }else {
				/**
				 * 清除cookie信息，如果这次用户没有选择记住用户名和密码，应该清除上一次存储cookie中用户名和密码
				 */
				// 如果用户没有选择记住用户名和密码，将原来存储在cookie中的用户名和密码清除
				// 1.怎么清楚cookie，就是创建和原来名称一样cookie，存储null,将原来覆盖，并且设置存储时间为0
				Cookie cookie_adminName = new Cookie("adminUserName", null);
				Cookie cookie_adminPassword = new Cookie("adminUserPassword", null);
				// 2.设置存储时间为0
				cookie_adminName.setMaxAge(0);
				cookie_adminPassword.setMaxAge(0);
				// 3.设置cookie的携带路径
				cookie_adminName.setPath(request.getContextPath());
				cookie_adminPassword.setPath(request.getContextPath());
				// 4.将该cookie添加到客户端
				response.addCookie(cookie_adminName);
				response.addCookie(cookie_adminPassword);
			}
            //提示用户登录成功,跳转页面让前台去实现
			return "{'success':true,'loginInfo':'登录成功！'}";
		}else
		{
			//登录失败，提示用户名或者密码错误
			return "{'success':false,'loginInfo':'账号或者密码错误！'}";
			
		}
		
		
	}
	
	/**
	 * 点击首页的安全退出，退出登录
	 */
	@RequestMapping("/deleteAdminUser")
	public String deleteAdminUser(HttpServletRequest request){
		//安全退出登录，就是将存储在session中的用户账号删除，重定向到登录界面
		HttpSession session = request.getSession();
		//1.将存储在session中管理员账号删除
		session.removeAttribute("adminUser");
		//2.重定向到登录界面
		return "redirect:adminLogin";
	}
	
	/**
	 * 修改密码
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/updateAdminPassword",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getNewAdminPassword(String newAdminPassword,HttpServletRequest request)
	{
		//获取存储在session中的管理用户
		HttpSession session = request.getSession();
		Admin adminUser=(Admin) session.getAttribute("adminUser");
		//获取该用户的id
		int admin_id = adminUser.getId();
		//将管理员用户id和新密码传输到service层
		int row=adminUserService.updateAdminPassword(admin_id,newAdminPassword);
		//对row进行判断，row为更新数据库影响的行数，如果更新成功row=1>0，失败row=0
		if(row>0)
		{
			//修改密码成功，更新成功，返回json给前台提示用户修改密码成功！,这里返回一个json格式的字符串（json对象形式）
			return "{'success':true,'updateAdminPasswordInfo':'修改密码成功！'}";
		}else
		{
			//修改密码失败，更新失败
			return "{'success':false,'updateAdminPasswordInfo':'修改密码失败！'}";
		}
		
	}
	
	
	/**
	 * 后台首页，修改管理员个人信息，进行查询回显个人信息
	 */
	@RequestMapping("/selectAdminInfo")
	@ResponseBody  //将admin对象转换成json格式对象返回页面
	//通过id查询管理员个人信息，进行页面回显
	public Admin selectAdminInfoById(int admin_id)
	{
		//根据管理员id查询该管理员的个人信息
		Admin admin=adminUserService.selectAdminInfoById(admin_id);
		return admin;
	}
	
	/**
	 * 修改管理员个人信息，根据获取的信息更新到数据库
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/editAdminInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditAdminInfo(Admin admin)
	{
		//将封装的管理员信息传输到service层,如果更新成功row=1>0,否则失败row==0
		//row是代表insert，update，delete操作数据库返回影响数据库的行数
		int row=adminUserService.updateAdminInfo(admin);
		if(row>0)
		{
			//修改个人信息成功，使用json格式字符串提示前台用户
			return "{'success':true,'editAdminInfo':'修改成功!'}";
		}else
		{
			//修改失败
			return "{'success':false,'editAdminInfo':'修改失败!'}";
		}
		
	}
}
