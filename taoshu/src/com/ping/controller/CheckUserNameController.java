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
 * 1.�û�ע��ajaxͬ���ж��û��Ƿ����
 * 2.�û�ע��
 * 3.�û���¼
 * 4.�˳���¼
 */
@Controller
public class CheckUserNameController {

	@Autowired
	private UserService userService;

	/**
	 * 1.����û����Ƿ����
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	public boolean getUserInfo(String username) {
		boolean checkUserIsExist = userService.checkUserIsExist(username);
		return checkUserIsExist;
	}

	/**
	 * 2.�û�ע��
	 */
	@RequestMapping("/registerUser")
	public String getRegisterInfo(User user, String validatecode, HttpServletRequest request, Model model) {
		// ������֤���Ƿ���ȷ
		// ��ȡ���ɷ���session�е���֤��
		HttpSession session = request.getSession();
		String checkcode = (String) session.getAttribute("code");
		// �Ƚ���֤���Ƿ�һ��
		if (StringUtils.isEmpty(validatecode)) {
			model.addAttribute("registerinfo", "��֤�벻��Ϊ�գ�");
			return "register";
		} else if (!checkcode.equals(validatecode)) {
			// ���������֤�벻һ�£������ʾ��Ϣ
			model.addAttribute("registerinfo", "���������֤�벻��ȷ,ע�����ִ�Сд��");
			return "register";
		} else {
			// �������֤����ȷ�����û���Ϣ���뵽���ݿ�
			boolean isNotSuccess = userService.addRegisterInfo(user);
			if (isNotSuccess) {
				//System.out.println("ע��ɹ���");
				return "registerSuccess";
			} else {
				//System.out.println("ע��ʧ�ܣ�");
				return "registerFail";
			}

		}

	}

	/**
	 * 3.�û���¼
	 * 
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/loginUser")
	public String login(User user1, HttpServletRequest request, HttpServletResponse response, String validatecode,
			Model model) throws UnsupportedEncodingException {
		// ��ȡcontroller������֤��
		HttpSession session = request.getSession();
		String checkImg = (String) session.getAttribute("code");
		// �ж��������֤���Ƿ�Ϊ��
		if (StringUtils.isEmpty(validatecode)) {
			// ����ǿգ����ص���¼ҳ�棬���������ʾ��Ϣ
			model.addAttribute("logininfo", "��֤�벻��Ϊ�գ�");
			return "login";
		}
		// �ж�������֤������ɵ���֤���Ƿ�һ��
		else if (!checkImg.equals(validatecode)) {
			// �����һ�¾ͷ��ص�¼ҳ�����������ʾ��Ϣ
			model.addAttribute("logininfo", "�������֤�벻��ȷ,ע�����ִ�Сд!");
			return "login";
		} else {
			// ִ�е����������֤�����벻Ϊ�ղ�����ȣ�������Ч���û���������
			// ͨ���û����������ѯ���ݿ��Ƿ���ڸ��û�
			User isExistUser = userService.selectByUserNameAndPassword(user1);
			if (isExistUser != null) {
				// ��¼�ɹ��ͽ��û����ڵ�session�У��Ա������ж��û��Ƿ��¼
				session.setAttribute("user", isExistUser);
				//��¼�ɹ��������������飬�ж��û��Ƿ�ѡ���ס�û��������룬�Ƿ�ѡ���Զ���¼
				
				//1. �ж��û��Ƿ�ѡ���ס�û���������
				String parameter = request.getParameter("remember_me");
				if (parameter != null) {
					// ����cookie�ǲ��ܴ洢���ģ��������ĵ��û���Ҫ���б���
					String encodeUserName = URLEncoder.encode(isExistUser.getUsername(), "UTF-8");
					// 1.�û�ѡ���ס�û��������룬����cookie���û���������洢��cookie��
					Cookie cookie_username = new Cookie("username1", encodeUserName);
					Cookie cookie_password = new Cookie("password1", isExistUser.getPassword());
					// 2.����cookie�Ĵ洢ʱ��,����Ϊ��λ������Ϊһ��Сʱ
					cookie_username.setMaxAge(60 * 60);
					cookie_password.setMaxAge(60 * 60);
					// 3.����cookie��Я��·��
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.��cookie��ӵ��ͻ����������
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);

				} else {
					/**
					 * ���cookie��Ϣ���������û�û��ѡ���ס�û��������룬Ӧ�������һ�δ洢cookie���û���������
					 */
					// ����û�û��ѡ���ס�û��������룬��ԭ���洢��cookie�е��û������������
					// 1.��ô���cookie�����Ǵ�����ԭ������һ��cookie���洢null,��ԭ�����ǣ��������ô洢ʱ��Ϊ0
					Cookie cookie_username = new Cookie("username1", null);
					Cookie cookie_password = new Cookie("password1", null);
					// 2.���ô洢ʱ��Ϊ0
					cookie_username.setMaxAge(0);
					cookie_password.setMaxAge(0);
					// 3.����cookie��Я��·��
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.����cookie��ӵ��ͻ���
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);
				}
				
				// �ж��û��Ƿ�ѡ���Զ���¼
				String autoLogin = request.getParameter("autologin");
				if (autoLogin != null) {
					// ˵���û�ѡ���Զ���¼�����û����û���������洢��cookie��
					// ����cookie�ǲ��ܴ洢���ģ��������ĵ��û���Ҫ���б���
					String encodeUserName = URLEncoder.encode(isExistUser.getUsername(), "UTF-8");
					// 1.�û�ѡ���ס�û��������룬����cookie���û���������洢��cookie��
					Cookie cookie_username = new Cookie("username2", encodeUserName);
					Cookie cookie_password = new Cookie("password2", isExistUser.getPassword());
					// 2.����cookie�Ĵ洢ʱ��,����Ϊ��λ������Ϊһ��Сʱ
					cookie_username.setMaxAge(60 * 60);
					cookie_password.setMaxAge(60 * 60);
					// 3.����cookie��Я��·��
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.��cookie��ӵ��ͻ����������
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);

				} else {
					/**
					 * ���cookie��Ϣ,���û��ѡ���Զ���¼��Ӧ�������һ��ѡ���Զ���¼��cookie��Ϣ
					 */
					// ����û�û��ѡ���ס�û��������룬��ԭ���洢��cookie�е��û������������
					// 1.��ô���cookie�����Ǵ�����ԭ������һ��cookie���洢null,��ԭ�����ǣ��������ô洢ʱ��Ϊ0
					Cookie cookie_username = new Cookie("username2", null);
					Cookie cookie_password = new Cookie("password2", null);
					
					// 2.���ô洢ʱ��Ϊ0
					cookie_username.setMaxAge(0);
					cookie_password.setMaxAge(0);
					// 3.����cookie��Я��·��
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 4.����cookie��ӵ��ͻ���
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);
					
				}
				
				//��¼�ɹ��ض�����ҳ
				return "redirect:index";
			} else {
				model.addAttribute("logininfo", "�û����������������������룡");
				return "login";
			}
		}

	}

	/**
	 * 4.�˳���¼�����ǽ��洢��session�е��û�ɾ�������൱��ע���û�
	 */
	@RequestMapping("/deleteUser")
	public String deleteUserInfo(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		//���cookie���û��Զ���¼���û���������
		/**
		 * ���cookie��Ϣ,�Զ���¼����ѡ���˳���¼Ӧ�������cookie��Ϣ��
		 * ��Ȼÿ�η�����Ŀ�κζ�����������������ȡcookie���û��������룬
		 * ÿ�ζ���¼��ÿ�δ洢�û���session��ʹ���˳���¼���û��Ч��
		 */
		// ����û�û��ѡ���ס�û��������룬��ԭ���洢��cookie�е��û������������
		// 1.��ô���cookie�����Ǵ�����ԭ������һ��cookie���洢null,��ԭ�����ǣ��������ô洢ʱ��Ϊ0
		Cookie cookie_username = new Cookie("username2", null);
		Cookie cookie_password = new Cookie("password2", null);
		//2.���ô洢ʱ��Ϊ0
		cookie_username.setMaxAge(0);
		cookie_password.setMaxAge(0);
		//3.����cookie��Я��·��
		cookie_username.setPath(request.getContextPath());
		cookie_password.setPath(request.getContextPath());
		//4.����cookie��ӵ��ͻ���
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		/**
		 * ��ס�û���������Ͳ�Ҫ���cookie����Ȼ����˳���¼����ʾ��¼ҳ����޷���cookie�л�ȡ�û�����������ʾ���ؼ���
		 */
		return "redirect:login";
	}
	
}
