package com.ping.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminOrderManageMapper;
import com.ping.pojo.Category;
import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;

/**
 * ��̨��������
 * @author admin
 *
 */
@Service
public class AdminOrderManageServiceImpl implements AdminOrderManageService {

	@Autowired
	private AdminOrderManageMapper adminOrderManageMapper;
	
	//Ĭ�ϲ�ѯ���ж�����Ϣ�е�ǰҳ�Ķ�����Ϣ������������������žͲ�ѯ�ö����Ŷ�����Ϣ
	@Override
	public AdminPageBean getOrderListPageBean(int currentPage, int pageSize, String oid) {
		//��Ҫ�κ��Ǵ�����ҳ���󲢷�װ��������,������ҳ������Ҫȷ�����͵�����
		AdminPageBean<Order> pageBean=new AdminPageBean<Order>();
		//1.��ѯ��ҳ��������total�ܼ�¼������װ
		int total=adminOrderManageMapper.selectOrderTotal(oid);
		//��װ����ҳ����
		pageBean.setTotal(total);
		//2.��ѯ��ҳ��������rows��ǰҳ�ĵ����ݲ���װ
		//��ҳ��ѯmysql���ݿ�sql�����Ҫȷ��limit ������������������һ����ǰҳ��ʼλ�ã���Ҫ���㣩���ڶ���������ÿҳ��ʾ������pageSize
		int currentStart=(currentPage-1)*pageSize;//��ʾ��ǰҳ��ʼλ������
		//����dao��ѯ��ǰҳ����
		List<Order> orderList=adminOrderManageMapper.selectOrderListInfo(currentStart,pageSize,oid);
		//����mysql�е�datetimeʱ�����ͻ�����������.0������Ҫ��ʽ��һ��ʱ��
		for(Order order:orderList)
		{
			
			String ordertime = order.getOrdertime();
			//���������͵Ķ���ʱ���ʽ��һ��
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//�ٽ���ʽ���õ��ַ���ת����date���ͣ��洢��order����Ķ���ʱ������
			Date neworderTime=null;
			try {
				neworderTime = dateFormat.parse(ordertime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�ٽ�date���ͱ���ַ����洢��order����Ķ���ʱ����������
			String formatOrderTime = dateFormat.format(neworderTime);
			order.setOrdertime(formatOrderTime);
		
		}
	   
		//��װ��ҳ����ǰҳ����
		pageBean.setRows(orderList);
		//3.���ط�ҳ����
		return pageBean;
	}

	//ͨ�������Ų�ѯ�ö������ж�������Ϣ���漰������ѯ���ͷ�ҳ��ѯ
	@Override
	public AdminPageBean getOrderItemListPageBean(int currentPage, int pageSize, String oid) {
		//��Ҫ�κ��Ǵ�����ҳ���󲢷�װ��������,������ҳ������Ҫȷ�����͵�����
		AdminPageBean<OrderItem> pageBean=new AdminPageBean<OrderItem>();
		//1.��ѯ��ҳ��������total�ܼ�¼������װ
		int total=adminOrderManageMapper.selectOrderItemTotal(oid);
		//��װ����ҳ����
		pageBean.setTotal(total);
		//2.��ѯ��ҳ��������rows��ǰҳ�ĵ����ݲ���װ
		//��ҳ��ѯmysql���ݿ�sql�����Ҫȷ��limit ������������������һ����ǰҳ��ʼλ�ã���Ҫ���㣩���ڶ���������ÿҳ��ʾ������pageSize
		int currentStart=(currentPage-1)*pageSize;//��ʾ��ǰҳ��ʼλ������
		//����dao��ѯ��ǰҳ����
		List<OrderItem> orderItemList=adminOrderManageMapper.selectOrderItemListInfo(currentStart,pageSize,oid);
		//��װ��ҳ����ǰҳ����
		pageBean.setRows(orderItemList);
		//3.���ط�ҳ����
		return pageBean;
	}

}
