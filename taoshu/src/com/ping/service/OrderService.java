package com.ping.service;

import java.util.List;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Order;
import com.ping.pojo.User;

/**
 * 处理订单信息
 * @author admin
 *
 */
public interface OrderService {

	//创建或插入用户提交订单
	public void insertOrder(Order order);
   
	/**
	 * 分页查询该用户的订单信息
	 */
    //根据用户uid和当前页信息查询该用户的当前页的订单信息，将数据封装到pageBean分页对象
	public PageBean selectOrderListByUidAndCurrentPage(User user, int currentPage, int currentCount);

	//根据订单号删除订单和订单项表里面相应的订单项
	public void deleteThisOrderByOid(String oid);
	
}
