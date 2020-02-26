package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;

/**
 * ��̨��������
 * @author admin
 *
 */
public interface AdminOrderManageMapper {

	//����û�û������������ѯ���ж�����������������û�����Ͳ�ѯ�ö���������
	int selectOrderTotal(@Param("oid")String oid);

	//����û�û������������ѯ���ж����е�ǰҳ�Ķ�����Ϣ�������ѯ������������Ϣ�еĵ�ǰҳ������Ϣ
	List<Order> selectOrderListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("oid") String oid);

	//ͨ�������Ų�ѯ�ö�����Ӧ�������������
	int selectOrderItemTotal(String oid);

	//ͨ�������Ų�ѯ����������Ʒ�����ѯ����Ϣ
	List<OrderItem> selectOrderItemListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("oid") String oid);

}
