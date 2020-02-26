package com.lianwei.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lianwei.store.domain.Order;
import com.lianwei.store.domain.OrderItem;
import com.lianwei.store.domain.User;

public interface OrderDao {

	void saveOrder(Connection conn, Order order) throws SQLException;

	void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

	int findOrderCountWithUid(User user) throws SQLException;

	List<Order> findOrderWithNumAndUser(User user,int startIndex,int pageSize) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

}
