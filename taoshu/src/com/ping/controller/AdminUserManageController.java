package com.ping.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.User;
import com.ping.service.AdminUserManageService;

@Controller
public class AdminUserManageController {
	//ʹ��ע���Զ��������ͽ���ע��service����
	@Autowired
	private AdminUserManageService  adminUserManageService;

	/**
	 * ��ѯ�û��б�����ҳ��ʾ�û���Ϣ
	 */
	@RequestMapping("/selectUserListInfo")
	@ResponseBody   //��AdminPageBean��ҳ����ת����json��ʽ���ݷ���ҳ��
	//pageΪeasyUIǰ̨�ύ�ĵ�ǰҳ��rowsΪÿҳ��ʾ����������������easyUI��̶ܹ�
	public AdminPageBean  getSelectUserListInfo(int page,int rows,String username)
	{
		int currentPage=page;//��ȡǰ̨�ĵ�ǰҳ
		int pageSize=rows;//ÿҳ��ʾ������
		//�������ݴ��䵽service��,��÷�װ�õ��û��б��ҳ����
		AdminPageBean pageBean=adminUserManageService.getUserListPageBean(currentPage,pageSize,username);
		return pageBean;
	}
	
	/**
	 * ����Ա����û����������û������û����Ƿ����
	 */
	@RequestMapping("/checkUserIsExist")
	@ResponseBody
	public boolean getUserName(String username)
	{
		//����service��ѯ���û����Ƿ����,����Boolean����true��false
		boolean checkUserIsExist=adminUserManageService.checkUserIsExist(username);
		return checkUserIsExist;
	}
	/**
	 * ����Ա����û�
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/addUserInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody  //����springmvc��ͼ������������json���ݶ�����ҳ��
	public String getAddUserInfo(User user)
	{
		//����װ�õ��û���Ϣ���䵽service�㣬�������ݿ�,mybatis�᷵��һ��intֵ���row=1>0��ʾ����ɹ����������ʧ��
		int row=adminUserManageService.addUserInfo(user);
		if(row>0)
		{
			//����û��ɹ�������json��ʽ�ַ�������ǰ̨ajax�ص�����
			return "{'success':true,'addUserInfo':'��ӳɹ���'}";
		}else
		{
			//����û�ʧ�ܣ�����json��ʽ�ַ�������ǰ̨ajax�ص�����
			return "{'success':false,'addUserInfo':'���ʧ�ܣ�'}";
		}
	}
	
	/**
	 * ����Ա�༭�û���ͨ��ǰ̨�����uid��ѯ���û���Ϣ�����ҷ���json����
	 */
	@RequestMapping("/selectUserInfo")
	@ResponseBody   //��user����ת����json��ʽ�Ķ���
	public User selectUserInfoByUid(int uid)
	{
		//ͨ��uid��ѯ���û�����Ϣ
		User user=adminUserManageService.selectUserInfoByUid(uid);
		//�����û�����ͨ��ע��@ResponseBodyת����json��ʽ�ַ�������ǰ̨
		return user;
	}
	/**
	 * �༭�û������û���Ϣ�����ݿ�
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/editUserInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditUserInfo(User user)
	{
		//����װ�õ�user�����䵽service��,ͨ��uid�����û���Ϣ
		int row=adminUserManageService.updateUserInfo(user);
		//mybatis�������ݿ�����ɹ��᷵��һ��int���͵�ֵ��ʹ��row�������row=1>0��ʾ���³ɹ����������ʧ��
		if(row>0)
		{
			//���³ɹ�������json��ʽ�����ַ�����ǰ̨��ʾ�û�
			return "{'success':true,'editUserInfo':'�޸ĳɹ���'}";
		}else
		{
			//����ʧ��
			return "{'success':false,'editUserInfo':'�޸�ʧ�ܣ�'}";
		}
	}
	
	/**
	 * ɾ���û���Ϣ����ѡ�е��û������ݿ���ɾ��
	 */
	@RequestMapping(value="/deleteUserInfo")
	@ResponseBody
	public String getDeleteUserInfo(String strUid)
	{
		//���ַ������ն��Ž��зָ�
		String[] arrayUid = strUid.split(",");
		//�����鴫�䵽service��
		int row=adminUserManageService.deleteUserInfo(arrayUid);
        //���row>0��ʾɾ���ɹ�������ʧ��
		if(row>0)
		{   //��дjson��ʽ�Ķ���jQuery����ʶ������,���ֶ��˫����ʹ��ת���ַ�\�������easyUI���ajax�ص��Ͳ��������ӣ�����ʶ������
			return "{\"success\":true,\"deleteUserInfo\":\"ɾ���ɹ���\"}";
		}else
		{
			return "{\"success\":false,\"deleteUserInfo\":\"ɾ��ʧ�ܣ�\"}";
		}
		
	}
}
