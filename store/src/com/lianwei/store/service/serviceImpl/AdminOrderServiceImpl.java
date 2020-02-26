package com.lianwei.store.service.serviceImpl;

import java.util.List;

import com.lianwei.store.dao.AdminOrderDao;
import com.lianwei.store.dao.daoImpl.AdminOrderDaoImpl;
import com.lianwei.store.domain.Order;
import com.lianwei.store.service.AdminOrderService;


public class AdminOrderServiceImpl implements AdminOrderService {

	@Override
	public List<Order> findAllOrder()  throws Exception{
		AdminOrderDao  adminOrderDao = new AdminOrderDaoImpl();
		List<Order> list = adminOrderDao.findAllOrder();
		return list;
	}

	@Override
	public List<Order> findStateOrder(String state) throws Exception {
		AdminOrderDao  adminOrderDao = new AdminOrderDaoImpl();
		List<Order> list = adminOrderDao.findStateOrder(state);
		return list;
	}

}
