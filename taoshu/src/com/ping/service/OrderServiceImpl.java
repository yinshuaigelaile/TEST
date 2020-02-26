package com.ping.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.PageBean;
import com.ping.mapper.OrderMapper;
import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;
import com.ping.pojo.User;
/**
 * ��������Ϣ
 * @author admin
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	//�ύ���������û�������Ϣ�������ݿ�����Ķ�����Ͷ������
	@Override
	public void insertOrder(Order order) {
		//����dao�㽫������Ϣ���뵽������
	    orderMapper.insertOrder(order);
		//����dao�㽫������Ϣ���뵽�������������ϸ��
		//�������������ȡ�������ж�����ϣ��������ϻ�ȡÿһ����������󣬽�����������䵽dao����뵽���ݿ�
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem orderItem:orderItems)
		{
			orderItem.setOrder(order);
			//ͨ��ѭ�������򶩵�����������
			orderMapper.insertOrderItem(orderItem);
		}
		
	}
	/**
	 * ��ҳ��ѯ���û��Ķ�����Ϣ
	 * 
	 * ��Ҫ˼�����ȷ�ҳ��ѯ������洢��list�����У�Ȼ��������ϻ�ȡÿ������������ÿ�������Ķ�����
	 * ��ѯÿ�������Ķ���������Ʒ��Ȼ���װ��������orderitem���󼯺��У��ٽ����ϴ洢����������order�Ķ����������
	 * �ٽ��������ϴ浽��pageBean����������У����ص�web��ת����ҳ�������ʾ
	 */
   //�����û�uid�͵�ǰҳ��Ϣ��ѯ���û��ĵ�ǰҳ�Ķ�����Ϣ�������ݷ�װ��pageBean��ҳ����
	@Override
	public PageBean selectOrderListByUidAndCurrentPage(User user, int currentPage, int currentCount) {
           //�����û���uid���е�ǰҳ��Ϣ��ѯ���û���ǰҳ�Ķ�����Ϣ������װ��pageBean���󣬲�����
		//����pageBean�����װ������Ҫ��ѯ������,������ҳ����Ҫȷ����������
		PageBean<Order> pageBean=new PageBean<Order>();
		//��װ��ҳ����pageBean,����Ҫ��ʾ�����ݲ�ѯ������װ����
		//private int currentPage;//��ǰҳ��
		pageBean.setCurrentPage(currentPage);
		//private int currentCount; //ÿҳ��ʾ������
		pageBean.setCurrentCount(currentCount);
		//private int totalCount;//������Ʒ��������
		//����uid��ѯ���û�������������,����dao��ķ���
		 int totalCount=orderMapper.selectOrderTotalCountByUid(user);
		 pageBean.setTotalCount(totalCount);
		//private int totalPage;//������Ʒ����ҳ��
		//���ݲ�ѯ���������������ҳ��,����Ǽ��㹫ʽ
		 int totalPage=(totalCount+currentCount-1)/currentCount;
		 pageBean.setTotalPage(totalPage);
		//private List<T> list;//ÿҳ��ʾ����Ʒ��Ϣ
		//mysql��ҳ��ѯlimit ?,?,����dao�㴫����Ҫ�����һ��������ֵ���ڶ�������ÿҳ��ʾ������
		 //��һ������Ϊ��ǰҳ��λ�ã�=����ǰҳ-1��*ÿҳ��ʾ������,���ݿ������������0��ʼ
		 int currentStart=(currentPage-1)*currentCount;
		 //��ȡ�û���uid
		 Integer uid = user.getUid();
		 List<Order> orderList=orderMapper.selectOrderByUidAndCurrentPage(uid,currentStart,currentCount);
		 //����ֻ�ǲ�ѯ���û���������ĳһҳ������������Ϣ����Ҫ��ѯ��������Ķ��������Ʒ��Ϣ
		 //�������ϵõ�ÿһ���������󣬲�ѯ�ö��������ж�����ҽ��з�װ����������
		 //����һ�·ǿ��жϣ�����û�û�ж�����orderList��Ϊ��
		 if(orderList!=null && orderList.size()>0)
		 {
			 //����洢������Ԫ�ؾͱ�������
			 for(Order order:orderList)
			 {
				 
				 //�õ�ÿ�������󣬸��ݶ���oid��ѯ�ö��������ж�����
				 //�Ȼ�ȡÿ�������Ķ�����
				 String oid = order.getOid();
				 //����oid��ѯ���������ж�������󣬴洢��list������
				List<OrderItem> orderItem=orderMapper.selectOrderItemByOid(oid);
				//�ٽ��ö�����ѯ�������ж������װ��order��������Ķ������
				order.setOrderItems(orderItem);
				
				//��ȡordertimeʱ�����������С������и�ʽ��һ��,mysql�Զ���ȷ�����룬���������.0,�Լ���ʽ��һ��
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
		 }
		 
		 //������ɶ�������orderList�����ÿ����������order���������ݷ�װ
		 //��������ļ��ϴ洢����ҳ�������������
		 //�ڷ�װ���������Ѻ��ж�һ�£�1.�û�����û�ж�������������û��Ԫ�أ��Ͳ���װ
		//                      2.�û��ж��������Ǿͷ�װ��pageBean��������
		 //����û��ж�����������������洢��������orderList���ж��Ƿ񼯺������Ƿ���Ԫ�أ��оʹ洢��װ��pageBean
		 if(orderList.size()>0)
		{
			 //˵���������󼯺�����Ԫ�أ�Ӧ�÷�װ��pageBean�������棬���û�оͲ���װ��
			 pageBean.setList(orderList);
		}
		
		//���ط�ҳ����
		return pageBean;
	}
	
	/**
	 *   ���ݶ�����ɾ���ö���,���һ�Ҫ���ö�����صĶ�����Ӷ��������ɾ��
	 *   
	 *   ����Է�������delete*������xml�������ú�������spring��������,���Զ���ʼ�ر��ύ����
	 */
	
	@Override
	public void deleteThisOrderByOid(String oid) {
		
		
		//���ݶ�������ɾ���ӱ������������ö������еĶ����Ȼ����ɾ������������ö�����¼
		//(��ɾ���ӱ�orderItem)ͨ��������oid,ɾ����������������ڸö����ļ�¼
		orderMapper.deleteOrderItemByOid(oid);
		
		//(��ɾ������orders)
		orderMapper.deleteThisOrderByOid(oid);
		
		
	}

}
