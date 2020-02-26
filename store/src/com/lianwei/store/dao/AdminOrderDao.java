package com.lianwei.store.dao;

import java.util.List;

import com.lianwei.store.domain.Order;

public interface AdminOrderDao {

	List<Order> findAllOrder() throws Exception;

	List<Order> findStateOrder(String state) throws Exception;

}
