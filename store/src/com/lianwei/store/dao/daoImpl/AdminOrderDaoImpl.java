package com.lianwei.store.dao.daoImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.lianwei.store.dao.AdminOrderDao;
import com.lianwei.store.domain.Order;
import com.lianwei.store.domain.OrderItem;
import com.lianwei.store.domain.Product;
import com.lianwei.store.utils.JDBCUtils;

public class AdminOrderDaoImpl implements AdminOrderDao {

	@Override
	public List<Order> findAllOrder() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class));
		for (Order order : list) {
			sql = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
			List<Map<String, Object>> orderItemList = qr.query(sql,  new MapListHandler(),order.getOid());
			//将获取到的map集合遍历  分别设置到对应的订单项和商品中
			for (Map<String, Object> map : orderItemList) {
				Product product = new Product();
				OrderItem orderItem = new OrderItem();
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				BeanUtils.populate(product, map);
				BeanUtils.populate(orderItem, map);
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}	
		}
		return list;
	}

	@Override
	public List<Order> findStateOrder(String state) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where state=?";
		List<Order> list =  qr.query(sql, new BeanListHandler<Order>(Order.class),state);
		for (Order order : list) {
			sql = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
			List<Map<String, Object>> orderItemList = qr.query(sql,  new MapListHandler(),order.getOid());
			//将获取到的map集合遍历  分别设置到对应的订单项和商品中
			for (Map<String, Object> map : orderItemList) {
				Product product = new Product();
				OrderItem orderItem = new OrderItem();
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				BeanUtils.populate(product, map);
				BeanUtils.populate(orderItem, map);
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}	
		}
		return list;
	}

}
