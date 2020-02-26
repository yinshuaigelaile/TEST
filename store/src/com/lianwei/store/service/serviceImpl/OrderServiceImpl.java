package com.lianwei.store.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lianwei.store.dao.OrderDao;
import com.lianwei.store.dao.daoImpl.OrderDaoImpl;
import com.lianwei.store.domain.Order;
import com.lianwei.store.domain.OrderItem;
import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.User;
import com.lianwei.store.service.OrderService;
import com.lianwei.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void saveOrder(Order order) throws SQLException {
		// 保存订单和订单下所有项，要么都成功，要么都失败，采取事务
//		try {
//			JDBCUtils.startTransaction();
//			OrderDao orderDao = new OrderDaoImpl();
//			orderDao.saveOrder(order);
//			for (OrderItem odrerItem : order.getList()) {
//				order.saveOrderItem(odrerItem);
//			}
//			JDBCUtils.commitAndClose();
//		} catch (SQLException e) {
//			
//			JDBCUtils.rollbackAndClose();
//		}
		
		//获取连接
		Connection conn = JDBCUtils.getConnection();
		try {
			
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			OrderDao orderDao = new OrderDaoImpl();
			orderDao.saveOrder(conn,order);
			//保存订单项
			for (OrderItem orderItem : order.getList()) {
				orderDao.saveOrderItem(conn,orderItem);
			}
			//提交
			conn.commit();
			
		} catch (SQLException e) {
			//回滚
			conn.rollback();
		}
	}

	@Override
	public PageModel findOrderWithNumAndUser(User user, int num) throws Exception {
		//查询出对应的记录数 才能进行分页   select count(*) from orders where uid=？
		OrderDao orderDao = new OrderDaoImpl();
		int totalRecords = orderDao.findOrderCountWithUid(user);
		//创建分页类
		PageModel pageModel = new PageModel(num, totalRecords, 10);
		//根据分页类去查询出对应的订单信息  select * from orders where uid=?
		List<Order> list = orderDao.findOrderWithNumAndUser(user,pageModel.getStartIndex(),5);
		//将订单信息设置到pageModel中  返回pageModel
		pageModel.setRecords(list);
		pageModel.setUrl("OrderServlet?method=findOrderWithNumAndUser");
		return pageModel;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		OrderDao orderDao = new OrderDaoImpl();
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		//更新对应的订单信息
		OrderDao orderDao = new OrderDaoImpl();
		orderDao.updateOrder(order);
	}

}
