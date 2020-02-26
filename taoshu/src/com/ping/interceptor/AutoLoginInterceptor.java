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
 * ����һ���Զ���¼�����������������з��ʸ���վ������ʵ���Զ���¼����ʵ���ǻ�ȡcookie���û��������뽫���洢��session��
 * ������ʵ���Զ���¼
 * @author admin
 *
 */
public class AutoLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
	
		HttpSession session = request.getSession();
		//ʵ���Զ���¼����ȡcookie�е��û���������
		Cookie[] cookies = request.getCookies();
		String username=null;
		String password=null;
		//�Ƚ��зǿ��ж�
		if(cookies!=null && cookies.length>0)
		{
			
				//����cookie����,
				for(Cookie cookie:cookies)
				{
					//�õ�ÿһ��cookie����
					if("username2".equals(cookie.getName()))
					{
						//�Ի�ȡcookie���û������н��룬��ֹ���ĳ�������
						username=URLDecoder.decode(cookie.getValue(), "UTF-8");
//						System.out.print("�û�����"+username);

					}
					if("password2".equals(cookie.getName()))
					{
						password=cookie.getValue();
//						System.out.println("���룺"+password);
					}
				}
					
			
		}
		//����û�������Զ���¼�ͻὫ�û���������洢��cookie�У��ͻ�ִ����������ȡcookie�û��������룬Ȼ��ִ�����������е�¼������ʲô����ִ�п���һ��
		if(username!=null && password!=null )
		{
			User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            //���û����������װ������Ȼ����Ϊ��¼�Ĳ�����ȥ���õ�¼�������е�¼
            User isExist = userService.selectByUserNameAndPassword(user);
            if(isExist!=null)
            {
            	/*System.out.println("��¼�ɹ���");*/
            	session.setAttribute("user", isExist);
            }
		}
		
		return true;
		//return true��ʾ���У�false��ʾ����
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
