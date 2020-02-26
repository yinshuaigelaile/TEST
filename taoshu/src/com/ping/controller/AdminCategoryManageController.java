package com.ping.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;
import com.ping.pojo.User;
import com.ping.service.AdminCategoryManageService;

/**
 * ��̨�������������ɾ�Ĳ�
 */
@Controller
public class AdminCategoryManageController {
	
	@Autowired
	private AdminCategoryManageService adminCategoryManageService;
	/**
	 * ��ѯ�����б�ͨ�������������ģ����ѯ����ҳ��ʾ������Ϣ
	 */
	//ͨ����������ģ����ѯ���з�����Ϣ,���ҽ��з�ҳ��ʾ�����û�������ѯ���з�����Ϣ���������Ա����������б�
	@RequestMapping("/selectCategoryListInfo")
	@ResponseBody   //��AdminPageBean��ҳ����ת����json��ʽ���ݷ���ҳ�棬easyUI���ݱ���Զ��������
	//pageΪeasyUIǰ̨�ύ�ĵ�ǰҳ��rowsΪÿҳ��ʾ����������������easyUI��̶ܹ�
	public AdminPageBean  getSelectCategoryListInfo(int page,int rows,String cname)
	{
		int currentPage=page;//��ȡǰ̨�ĵ�ǰҳ
		int pageSize=rows;//ÿҳ��ʾ������
		//�������ݴ��䵽service��,��÷�װ�õķ����б��ҳ����
		AdminPageBean pageBean=adminCategoryManageService.getCategoryListPageBean(currentPage,pageSize,cname);
		return pageBean;
	}
	
	/**
	 * ����Ա��ӷ��࣬������������Ƽ��������Ƿ����
	 */
	@RequestMapping("/checkCategoryIsExist")
	@ResponseBody
	public boolean getCategoryName(String cname)
	{
		//����service��ѯ�÷��������Ƿ����,����Boolean����true��false
		boolean checkCategoryIsExist=adminCategoryManageService.checkCategoryIsExist(cname);
		return checkCategoryIsExist;
	}
	
	/**
	 * ����Ա��ӷ���
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/addCategoryInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody  //����springmvc��ͼ������������json���ݶ�����ҳ��
	public String getAddCategoryInfo(Category category)
	{
		//����װ�õķ�����Ϣ���䵽service�㣬�������ݿ�,mybatis�᷵��һ��intֵ���row=1>0��ʾ����ɹ����������ʧ��
		int row=adminCategoryManageService.addCategoryInfo(category);
		if(row>0)
		{
			//����û��ɹ�������json��ʽ�ַ�������ǰ̨ajax�ص�����
			return "{'success':true,'addCategoryInfo':'��ӳɹ���'}";
		}else
		{
			//����û�ʧ�ܣ�����json��ʽ�ַ�������ǰ̨ajax�ص�����
			return "{'success':false,'addCategoryInfo':'���ʧ�ܣ�'}";
		}
	}
	
	/**
	 * ����Ա�༭���࣬ͨ��ǰ̨�����cid��ѯ�÷�����Ϣ�����ҷ���json����
	 */
	@RequestMapping("/selectCategoryInfo")
	@ResponseBody   //��user����ת����json��ʽ�Ķ���
	public Category selectCategoryInfoByUid(int cid)
	{
		//ͨ��cid��ѯ�÷������Ϣ
		Category category=adminCategoryManageService.selectCategoryInfoByCid(cid);
		//���ط������ͨ��ע��@ResponseBodyת����json��ʽ�ַ�������ǰ̨
		return category;
	}
	
	/**
	 * �༭���࣬��������Ϣ�����ݿ�
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/editCategoryInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditCategoryInfo(Category category)
	{
		//����װ�õ�category�����䵽service��,ͨ��cid�����û���Ϣ
		int row=adminCategoryManageService.updateCategoryInfo(category);
		//mybatis�������ݿ�����ɹ��᷵��һ��int���͵�ֵ��ʹ��row�������row=1>0��ʾ���³ɹ����������ʧ��
		if(row>0)
		{
			//���³ɹ�������json��ʽ�����ַ�����ǰ̨��ʾ�û�
			return "{'success':true,'editCategoryInfo':'�޸ĳɹ���'}";
		}else
		{
			//����ʧ��
			return "{'success':false,'editCategoryInfo':'�޸�ʧ�ܣ�'}";
		}
	}
	
	/**
	 * ɾ��������Ϣ����ѡ�еķ�������ݿ���ɾ��
	 */
	@RequestMapping(value="/deleteCategoryInfo")
	@ResponseBody
	public String getDeleteUserInfo(String strCid)
	{
		//���ַ������ն��Ž��зָ�
		String[] arrayCid = strCid.split(",");
		//�����鴫�䵽service��
		int row=adminCategoryManageService.deleteCategoryInfo(arrayCid);
        //���row>0��ʾɾ���ɹ�������ʧ��
		if(row>0)
		{   //��дjson��ʽ�Ķ���jQuery����ʶ������,���ֶ��˫����ʹ��ת���ַ�\�������easyUI���ajax�ص��Ͳ��������ӣ�����ʶ������
			return "{\"success\":true,\"deleteCategoryInfo\":\"ɾ���ɹ���\"}";
		}else
		{
			return "{\"success\":false,\"deleteCategoryInfo\":\"ɾ��ʧ�ܣ�\"}";
		}
		
	}
}
