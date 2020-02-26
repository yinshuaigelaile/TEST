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
    
	//ʹ��ע���Զ���������ע��service����
	@Autowired
	private AdminUserService adminUserService;
	/**
	 * ͨ��ajax����̨ע��Ĺ���Ա�˺��Ƿ����
	 */
	@RequestMapping("/checkAdminUserName")
	@ResponseBody   //ʹ�ø�ע�⽫����Boolean����ֵת����json��ʽ
	public boolean CheckAdminUserName(String admin_username)
	{
		//������Աע����˺ţ����䵽service�㣬��ѯ���ݿ��Ƿ���ڸ��˺�
		boolean checkAdminUserName = adminUserService.CheckAdminUserName(admin_username);
		return checkAdminUserName;
	}
	
	/**
	 * ����Աע���˺�
	 * �����ʸ�·����springmvc���Զ������ͷ�װ���������������
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/adminUserRegister",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getAdminRegisterInfo(Admin admin,String checkImg,HttpServletRequest request)
	{
		//��ȡ�û�ע����Ϣ�������ж��û��������֤���Ƿ���ȷ�������ȷ�ٽ������ݴ��䵽service�㣬������ʾ�û���֤�벻��ȷ
		//�Ա��û��������֤��ʹ洢��session�����ɵ���֤���Ƿ�һ��
		HttpSession session = request.getSession();
		//��ȡsession�д洢���ɵ���֤��
		String checkCode = (String) session.getAttribute("code");
		//�Ƚ��û��������֤���������֤���Ƿ�һ��,�Ƚ��ַ����Ƿ����,checkImgΪ��װǰ̨�û��������֤��
		if(!checkCode.equals(checkImg))
		{
			//��֤�벻��ȷ����ʾ�û��������֤�벻��ȷ�������൱������һ����
			//��Ҫ��ʾ����Ϣ��д��json��ʽ���ַ��������ظ�ǰ̨
			return "{'checkCode':true,'checkCodeInfo':'��֤�벻��ȷ��ע�����ִ�Сд��'}";//return�����������������벻��ִ��
			
		}
		
		//�ܹ���˵����֤����ȷ����ע����Ϣ���䵽service��
		int row=adminUserService.addAdminRegisterInfo(admin);
		//�ж�row�Ƿ����0������Ǿ�˵������ɹ�����ע��ɹ�������ע��ʧ��
		if(row>0)
		{
			//ע��ɹ�
			return "{'success':true,'registerInfo':'ע��ɹ���'}";
		}else
		{
			//ע��ʧ��
			return "{'success':false,'registerInfo':'ע��ʧ�ܣ�'}";
		}
		
	}
	
	/**
	 * ����Ա��¼
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/adminUserLogin",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody   //��ע�������ͼ����������ʱjson��������jspҳ��
	public String getAdminLoginInfo(Admin admin,String checkImg,HttpServletRequest request,HttpServletResponse response)
	{
		//1.�ж���֤���Ƿ���ȷ����ȡ�û��������֤������ɵ���֤����бȽϣ����ɵ���֤���Ǵ洢session��
		//��ȡsession�д洢���ɵ���֤��
		HttpSession session = request.getSession();
		String checkCode = (String) session.getAttribute("code");
		//�ж��������֤������ɵ���֤���Ƿ���ȷ
		if(!checkCode.equals(checkImg))
		{
			//�������ȣ�����ʾ�û���֤�����벻��ȷ��,return ����json��ʽ���ַ���
			return "{'checkCode':true,'checkCodeInfo':'��֤�벻��ȷ��ע�����ִ�Сд��'}";
		}
		//2.�жϹ���Ա�û����������Ƿ���ȷ,ͨ���û����������ѯ�Ƿ���ڸ��û�
		Admin isExistAdminUser=adminUserService.selectAdminUserByNameAndPassword(admin);
		//��ѯ���isExistAdminUser�Ƿ�Ϊ�գ������Ϊ��˵�����ڸ��û������򲻴���
		if(isExistAdminUser!=null)
		{
			//��¼�ɹ�����ʾ�û���¼�ɹ�,ǰ̨��ʾ������ת����ҳ
			//4.�������û���Ϣ�洢��session��
			session.setAttribute("adminUser", isExistAdminUser);
			//3.�ж��û��Ƿ�ѡ���ס�û�������
            String remember = request.getParameter("remember_me");
            if(remember!=null)
            {
            	//˵���û�ѡ���ס�û��������룬��Ҫ���û���������洢��cookie��ӵ��ͻ���
            	//3.1����cookie,������Ա���˺ź�����洢��cookie��
            	//�洢�˺ŵ�cookie
            	Cookie cookie_adminName=new Cookie("adminUserName",isExistAdminUser.getAdmin_name() );
            	//�洢���뵽cookie
            	Cookie cookie_adminPassword=new Cookie("adminUserPassword",isExistAdminUser.getAdmin_password());
            	//3.2����cookie�洢ʱ��,����Ϊ��λ��60*60Ϊһ��Сʱ
            	cookie_adminName.setMaxAge(60*60);
            	cookie_adminPassword.setMaxAge(60*60);
            	//3.3����cookieЯ��·��,����Ϊ���ʸù��̵�·��
            	cookie_adminName.setPath(request.getContextPath());
            	cookie_adminPassword.setPath(request.getContextPath());
            	//3.4�����úõ�cookie������ӵ��ͻ���
            	response.addCookie(cookie_adminName);
            	response.addCookie(cookie_adminPassword);
            }else {
				/**
				 * ���cookie��Ϣ���������û�û��ѡ���ס�û��������룬Ӧ�������һ�δ洢cookie���û���������
				 */
				// ����û�û��ѡ���ס�û��������룬��ԭ���洢��cookie�е��û������������
				// 1.��ô���cookie�����Ǵ�����ԭ������һ��cookie���洢null,��ԭ�����ǣ��������ô洢ʱ��Ϊ0
				Cookie cookie_adminName = new Cookie("adminUserName", null);
				Cookie cookie_adminPassword = new Cookie("adminUserPassword", null);
				// 2.���ô洢ʱ��Ϊ0
				cookie_adminName.setMaxAge(0);
				cookie_adminPassword.setMaxAge(0);
				// 3.����cookie��Я��·��
				cookie_adminName.setPath(request.getContextPath());
				cookie_adminPassword.setPath(request.getContextPath());
				// 4.����cookie��ӵ��ͻ���
				response.addCookie(cookie_adminName);
				response.addCookie(cookie_adminPassword);
			}
            //��ʾ�û���¼�ɹ�,��תҳ����ǰ̨ȥʵ��
			return "{'success':true,'loginInfo':'��¼�ɹ���'}";
		}else
		{
			//��¼ʧ�ܣ���ʾ�û��������������
			return "{'success':false,'loginInfo':'�˺Ż����������'}";
			
		}
		
		
	}
	
	/**
	 * �����ҳ�İ�ȫ�˳����˳���¼
	 */
	@RequestMapping("/deleteAdminUser")
	public String deleteAdminUser(HttpServletRequest request){
		//��ȫ�˳���¼�����ǽ��洢��session�е��û��˺�ɾ�����ض��򵽵�¼����
		HttpSession session = request.getSession();
		//1.���洢��session�й���Ա�˺�ɾ��
		session.removeAttribute("adminUser");
		//2.�ض��򵽵�¼����
		return "redirect:adminLogin";
	}
	
	/**
	 * �޸�����
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/updateAdminPassword",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getNewAdminPassword(String newAdminPassword,HttpServletRequest request)
	{
		//��ȡ�洢��session�еĹ����û�
		HttpSession session = request.getSession();
		Admin adminUser=(Admin) session.getAttribute("adminUser");
		//��ȡ���û���id
		int admin_id = adminUser.getId();
		//������Ա�û�id�������봫�䵽service��
		int row=adminUserService.updateAdminPassword(admin_id,newAdminPassword);
		//��row�����жϣ�rowΪ�������ݿ�Ӱ���������������³ɹ�row=1>0��ʧ��row=0
		if(row>0)
		{
			//�޸�����ɹ������³ɹ�������json��ǰ̨��ʾ�û��޸�����ɹ���,���ﷵ��һ��json��ʽ���ַ�����json������ʽ��
			return "{'success':true,'updateAdminPasswordInfo':'�޸�����ɹ���'}";
		}else
		{
			//�޸�����ʧ�ܣ�����ʧ��
			return "{'success':false,'updateAdminPasswordInfo':'�޸�����ʧ�ܣ�'}";
		}
		
	}
	
	
	/**
	 * ��̨��ҳ���޸Ĺ���Ա������Ϣ�����в�ѯ���Ը�����Ϣ
	 */
	@RequestMapping("/selectAdminInfo")
	@ResponseBody  //��admin����ת����json��ʽ���󷵻�ҳ��
	//ͨ��id��ѯ����Ա������Ϣ������ҳ�����
	public Admin selectAdminInfoById(int admin_id)
	{
		//���ݹ���Աid��ѯ�ù���Ա�ĸ�����Ϣ
		Admin admin=adminUserService.selectAdminInfoById(admin_id);
		return admin;
	}
	
	/**
	 * �޸Ĺ���Ա������Ϣ�����ݻ�ȡ����Ϣ���µ����ݿ�
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/editAdminInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditAdminInfo(Admin admin)
	{
		//����װ�Ĺ���Ա��Ϣ���䵽service��,������³ɹ�row=1>0,����ʧ��row==0
		//row�Ǵ���insert��update��delete�������ݿⷵ��Ӱ�����ݿ������
		int row=adminUserService.updateAdminInfo(admin);
		if(row>0)
		{
			//�޸ĸ�����Ϣ�ɹ���ʹ��json��ʽ�ַ�����ʾǰ̨�û�
			return "{'success':true,'editAdminInfo':'�޸ĳɹ�!'}";
		}else
		{
			//�޸�ʧ��
			return "{'success':false,'editAdminInfo':'�޸�ʧ��!'}";
		}
		
	}
}
