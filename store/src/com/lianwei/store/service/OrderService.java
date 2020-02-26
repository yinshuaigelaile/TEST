package com.lianwei.store.service;

import java.sql.SQLException;

import com.lianwei.store.domain.Order;
import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.User;

public interface OrderService {

	void saveOrder(Order order) throws SQLException;

	PageModel findOrderWithNumAndUser(User user, int num) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;
	
}
