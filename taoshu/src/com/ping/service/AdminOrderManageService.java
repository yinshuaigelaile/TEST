package com.ping.service;

import com.ping.common.utils.AdminPageBean;

/**
 * 后台订单管理service
 * @author admin
 *
 */
public interface AdminOrderManageService {

	//默认是查询所有订单中当前页的订单信息，如果用户输入订单号就查询该订单号订单信息
	AdminPageBean getOrderListPageBean(int currentPage, int pageSize, String oid);

	//通过订单号查询订单项表和商品表中该订单所有商品信息
	AdminPageBean getOrderItemListPageBean(int currentPage, int pageSize, String oid);

	
}
