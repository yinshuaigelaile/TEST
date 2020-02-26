package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;
import com.ping.pojo.User;

/**
 * ������dao
 * @author admin
 *
 */
public interface OrderMapper {

	//���û��ύ������Ϣ�������ݿ�Ķ�����,����ֵ��int�����������0˵������ɹ�
	public void  insertOrder(Order order);
	
	//���û��ύ�Ķ�����ϢҲҪ���붩�����������ϸ��
	public void insertOrderItem(OrderItem orderItem);

	//�����û���uid��ѯ���û��Ķ���������
	public int selectOrderTotalCountByUid(User user);
    
	//�����û���uid�͵�ǰҳ��ѯ��ҳ�û��Ķ�����Ϣ,mybatis�������������Ҫ��
	public List<Order> selectOrderByUidAndCurrentPage(@Param("uid") int uid,@Param("currentStart") int currentStart,@Param("currentCount") int currentCount);

	//���ݶ����Ų�ѯ�ö��������еĶ��������װ�õ�list����,����������Ʒ���ѯ��Ӧ����Ʒ��Ϣ
	public List<OrderItem> selectOrderItemByOid(String oid);

	//����oidɾ��������������Ǹö����ŵĶ������¼
	public void deleteOrderItemByOid(String oid);
    
	//����oidɾ��������order����ö�����¼
	public void deleteThisOrderByOid(String oid);
	
}
