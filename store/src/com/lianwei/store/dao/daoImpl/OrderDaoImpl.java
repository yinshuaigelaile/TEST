package com.lianwei.store.dao.daoImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lianwei.store.dao.OrderDao;
import com.lianwei.store.domain.Order;
import com.lianwei.store.domain.OrderItem;
import com.lianwei.store.domain.Product;
import com.lianwei.store.domain.User;
import com.lianwei.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		QueryRunner qr = new QueryRunner();
		int a = qr.update(conn, sql, params);
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getQuantity(),orderItem.getTotal(),orderItem.getProduct().getPid(),orderItem.getOrder().getOid()};
		QueryRunner qr = new QueryRunner();
		qr.update(conn, sql, params);
	}

	@Override
	public int findOrderCountWithUid(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long totalRecords =  (Long)qr.query(sql,new ScalarHandler(),user.getUid());
		return totalRecords.intValue();
	}
	
	/**
	 * 根据用户查询到对应的订单信息   遍历订单  查询到对应订单的订单项信息  将获取到的信息分别设置到对应的订单项和商品中  
	 * 将商品设置到订单项中 再将订单项设置到对应的订单中  将订单项集合返回
	 * @throws Exception 
	 */
	@Override
	public List<Order> findOrderWithNumAndUser(User user,int startIndex,int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid=? limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
		for (Order order : list) {
			//根据当前订单去查询对应的订单项  将其设置进去   select * from orders o,orderitem t where o.oid=t.itemid and o.uid=?
			sql = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
			List<Map<String, Object>> orderItemList = qr.query(sql,  new MapListHandler(),order.getOid());
			//将获取到的map集合遍历  分别设置到对应的订单项和商品中
			for (Map<String, Object> map : orderItemList) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				BeanUtils.populate(orderItem, map);
				BeanUtils.populate(product, map);
				orderItem.setProduct(product);
				
				order.getList().add(orderItem);
			}
		}
		return list;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		//根据当前订单去查询对应的订单项  将其设置进去   select * from orders o,orderitem t where o.oid=t.itemid and o.uid=?
		sql = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
		List<Map<String, Object>> orderItemList = qr.query(sql,new MapListHandler(),oid);
		//将获取到的map集合遍历  分别设置到对应的订单项和商品中
		for (Map<String, Object> map : orderItemList) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			BeanUtils.populate(orderItem, map);
			BeanUtils.populate(product, map);
			orderItem.setProduct(product);
			
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		//更新语句
		String sql = "update orders set ordertime=?,total=?,state=?,address=?,name=?,telephone=? where oid=?";
		Object[] params = {order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		qr.update(sql, params);
	}

}
