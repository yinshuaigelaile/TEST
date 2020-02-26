package com.ping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.AdminPageBean;
import com.ping.service.AdminCategoryManageService;
import com.ping.service.AdminOrderManageService;

/**
 * ��̨��������
 * @author admin
 *
 */
@Controller
public class AdminOrderManageController {

	@Autowired
	private AdminOrderManageService adminOrderManageService;
	/**
	 * ��ѯ�����б������Ը����û����붩���Ž��в�ѯ
	 */
	//����û����붩���žͲ�ѯ��ǰҳ�ö�����Ϣ�������ѯ���ж�����ǰҳ��Ϣ
	@RequestMapping("/selectOrderListInfo")
	@ResponseBody   //��AdminPageBean��ҳ����ת����json��ʽ���ݷ���ҳ�棬easyUI���ݱ���Զ��������
	//pageΪeasyUIǰ̨�ύ�ĵ�ǰҳ��rowsΪÿҳ��ʾ����������������easyUI��̶ܹ�
	public AdminPageBean  getSelectOrderListInfo(int page,int rows,String oid)
	{
		int currentPage=page;//��ȡǰ̨�ĵ�ǰҳ��Ĭ��ǰ̨�ύ��һҳ
		int pageSize=rows;//ÿҳ��ʾ��������Ĭ����ʾ10��
		//�������ݴ��䵽service��,��÷�װ�õĶ����б��ҳ����
		AdminPageBean pageBean=adminOrderManageService.getOrderListPageBean(currentPage,pageSize,oid);
		return pageBean;
	}
	
	/**
	 * ��ʾ�������飬�г�������������ж�����
	 */
	@RequestMapping("/selectOrderItemListInfo")
	@ResponseBody   //��AdminPageBean��ҳ����ת����json��ʽ���ݷ���ҳ�棬easyUI���ݱ���Զ��������
	//pageΪeasyUIǰ̨�ύ�ĵ�ǰҳ��rowsΪÿҳ��ʾ����������������easyUI��̶ܹ�
	public AdminPageBean  getSelectOrderItemListInfo(int page,int rows,String oid)
	{
		int currentPage=page;//��ȡǰ̨�ĵ�ǰҳ��Ĭ��ǰ̨�ύ��һҳ
		int pageSize=rows;//ÿҳ��ʾ��������Ĭ����ʾ10��
		//�������ݴ��䵽service��,��÷�װ�õĶ����б��ҳ����
		AdminPageBean pageBean=adminOrderManageService.getOrderItemListPageBean(currentPage,pageSize,oid);
		return pageBean;
	
		
	}
}
