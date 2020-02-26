package com.ping.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ping.pojo.User;
import com.ping.service.UserService;

/**
 * 定义一个自动登录的拦截器，拦截所有访问该网站的请求，实现自动登录，其实就是获取cookie的用户名和密码将它存储到session中
 * 这样就实现自动登录
 * @author admin
 *
 */
public class AutoLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
	
		HttpSession session = request.getSession();
		//实现自动登录，获取cookie中的用户名和密码
		Cookie[] cookies = request.getCookies();
		String username=null;
		String password=null;
		//先进行非空判断
		if(cookies!=null && cookies.length>0)
		{
			
				//遍历cookie数组,
				for(Cookie cookie:cookies)
				{
					//得到每一个cookie对象
					if("username2".equals(cookie.getName()))
					{
						//对获取cookie的用户名进行解码，防止中文出现乱码
						username=URLDecoder.decode(cookie.getValue(), "UTF-8");
//						System.out.print("用户名："+username);

					}
					if("password2".equals(cookie.getName()))
					{
						password=cookie.getValue();
//						System.out.println("密码："+password);
					}
				}
					
			
		}
		//如果用户名点击自动登录就会将用户名和密码存储到cookie中，就会执行上面代码获取cookie用户名和密码，然后执行下面代码进行登录，否则什么都不执行空走一遍
		if(username!=null && password!=null )
		{
			User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            //将用户名和密码封装到对象，然后作为登录的参数，去调用登录方法进行登录
            User isExist = userService.selectByUserNameAndPassword(user);
            if(isExist!=null)
            {
            	/*System.out.println("登录成功！");*/
            	session.setAttribute("user", isExist);
            }
		}
		
		return true;
		//return true表示放行，false表示拦截
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {

	}


}
