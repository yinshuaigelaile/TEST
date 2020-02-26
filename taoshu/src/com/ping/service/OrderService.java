package com.ping.service;

import java.util.List;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Order;
import com.ping.pojo.User;

/**
 * ��������Ϣ
 * @author admin
 *
 */
public interface OrderService {

	//����������û��ύ����
	public void insertOrder(Order order);
   
	/**
	 * ��ҳ��ѯ���û��Ķ�����Ϣ
	 */
    //�����û�uid�͵�ǰҳ��Ϣ��ѯ���û��ĵ�ǰҳ�Ķ�����Ϣ�������ݷ�װ��pageBean��ҳ����
	public PageBean selectOrderListByUidAndCurrentPage(User user, int currentPage, int currentCount);

	//���ݶ�����ɾ�������Ͷ������������Ӧ�Ķ�����
	public void deleteThisOrderByOid(String oid);
	
}
