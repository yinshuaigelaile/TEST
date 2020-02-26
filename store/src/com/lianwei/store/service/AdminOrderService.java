package com.lianwei.store.service;

import java.util.List;

import com.lianwei.store.domain.Order;

public interface AdminOrderService {

	List<Order> findAllOrder() throws Exception ;

	List<Order> findStateOrder(String state) throws Exception;

}
