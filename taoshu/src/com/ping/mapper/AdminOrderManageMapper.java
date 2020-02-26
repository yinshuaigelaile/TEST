package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;

/**
 * 后台订单管理
 * @author admin
 *
 */
public interface AdminOrderManageMapper {

	//如果用户没有输入条件查询所有订单的总条数，如果用户输入就查询该订单总条数
	int selectOrderTotal(@Param("oid")String oid);

	//如果用户没有输入条件查询所有订单中当前页的订单信息，否则查询该条件订单信息中的当前页订单信息
	List<Order> selectOrderListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("oid") String oid);

	//通过订单号查询该订单对应订单项的总条数
	int selectOrderItemTotal(String oid);

	//通过订单号查询订单项表和商品表多表查询的信息
	List<OrderItem> selectOrderItemListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("oid") String oid);

}
