package com.ping.service;

import com.ping.common.utils.AdminPageBean;

/**
 * ��̨��������service
 * @author admin
 *
 */
public interface AdminOrderManageService {

	//Ĭ���ǲ�ѯ���ж����е�ǰҳ�Ķ�����Ϣ������û����붩���žͲ�ѯ�ö����Ŷ�����Ϣ
	AdminPageBean getOrderListPageBean(int currentPage, int pageSize, String oid);

	//ͨ�������Ų�ѯ����������Ʒ���иö���������Ʒ��Ϣ
	AdminPageBean getOrderItemListPageBean(int currentPage, int pageSize, String oid);

	
}
