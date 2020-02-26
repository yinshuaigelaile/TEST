package com.ping.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.PageBean;
import com.ping.mapper.OrderMapper;
import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;
import com.ping.pojo.User;
/**
 * 处理订单信息
 * @author admin
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	//提交订单，将用户订单信息插入数据库里面的订单表和订单项表
	@Override
	public void insertOrder(Order order) {
		//调用dao层将订单信息插入到订单表
	    orderMapper.insertOrder(order);
		//调用dao层将订单信息插入到订单项表（订单明细表）
		//遍历订单对象获取里面所有订单项集合，遍历集合获取每一个订单项对象，将订单项对象传输到dao层插入到数据库
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem orderItem:orderItems)
		{
			orderItem.setOrder(order);
			//通过循环不断向订单项表插入数据
			orderMapper.insertOrderItem(orderItem);
		}
		
	}
	/**
	 * 分页查询该用户的订单信息
	 * 
	 * 主要思想是先分页查询订单表存储到list集合中，然后遍历集合获取每个订单，根据每个订单的订单号
	 * 查询每个订单的订单项表和商品表，然后封装到订单项orderitem对象集合中，再将集合存储到订单对象order的订单项集合属性
	 * 再将订单集合存到到pageBean对象的属性中，返回到web层转发到页面进行显示
	 */
   //根据用户uid和当前页信息查询该用户的当前页的订单信息，将数据封装到pageBean分页对象
	@Override
	public PageBean selectOrderListByUidAndCurrentPage(User user, int currentPage, int currentCount) {
           //根据用户的uid还有当前页信息查询该用户当前页的订单信息，并封装到pageBean对象，并返回
		//创建pageBean对象封装好所有要查询的数据,创建分页对象要确定泛型类型
		PageBean<Order> pageBean=new PageBean<Order>();
		//封装分页对象pageBean,所有要显示的数据查询出来封装起来
		//private int currentPage;//当前页数
		pageBean.setCurrentPage(currentPage);
		//private int currentCount; //每页显示的条数
		pageBean.setCurrentCount(currentCount);
		//private int totalCount;//该类商品的总条数
		//根据uid查询该用户订单的总条数,调用dao层的方法
		 int totalCount=orderMapper.selectOrderTotalCountByUid(user);
		 pageBean.setTotalCount(totalCount);
		//private int totalPage;//该类商品的总页数
		//根据查询的总条数计算出总页数,这个是计算公式
		 int totalPage=(totalCount+currentCount-1)/currentCount;
		 pageBean.setTotalPage(totalPage);
		//private List<T> list;//每页显示的商品信息
		//mysql分页查询limit ?,?,调用dao层传参需要计算第一个参数的值，第二个就是每页显示的条数
		 //第一个参数为当前页的位置，=（当前页-1）*每页显示的条数,数据库的数据索引从0开始
		 int currentStart=(currentPage-1)*currentCount;
		 //获取用户的uid
		 Integer uid = user.getUid();
		 List<Order> orderList=orderMapper.selectOrderByUidAndCurrentPage(uid,currentStart,currentCount);
		 //以上只是查询该用户订单里面某一页订单表里面信息，还要查询订单对象的订单项和商品信息
		 //遍历集合得到每一个订单对象，查询该订单的所有订单项并且进行封装到集合里面
		 //先做一下非空判断，如果用户没有订单，orderList就为空
		 if(orderList!=null && orderList.size()>0)
		 {
			 //如果存储并且有元素就遍历集合
			 for(Order order:orderList)
			 {
				 
				 //得到每订单对象，根据订单oid查询该订单的所有订单项
				 //先获取每个订单的订单号
				 String oid = order.getOid();
				 //根据oid查询订单的所有订单项对象，存储到list集合中
				List<OrderItem> orderItem=orderMapper.selectOrderItemByOid(oid);
				//再将该订单查询出的所有订单项封装到order对象里面的订单项集合
				order.setOrderItems(orderItem);
				
				//获取ordertime时间对象秒后面的小数点进行格式化一下,mysql自动精确到毫秒，在秒后面有.0,自己格式化一下
				String ordertime = order.getOrdertime();
				//将日期类型的订单时间格式化一下
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//再将格式化好的字符串转换成date类型，存储到order对象的订单时间属性
				Date neworderTime=null;
				try {
					neworderTime = dateFormat.parse(ordertime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//再将date类型变成字符串存储到order对象的订单时间属性里面
				String formatOrderTime = dateFormat.format(neworderTime);
				order.setOrdertime(formatOrderTime);
			
				
			 }
		 }
		 
		 //以上完成订单对象orderList里面的每个订单对象order的完整数据封装
		 //订单对象的集合存储到分页对象的属性里面
		 //在封装订单集合友好判断一下，1.用户可能没有订单，集合里面没有元素，就不封装
		//                      2.用户有订单，这是就封装到pageBean属性里面
		 //如果用户有订单，订单集合里面存储订单对象orderList，判断是否集合里面是否有元素，有就存储封装到pageBean
		 if(orderList.size()>0)
		{
			 //说明订单对象集合中有元素，应该封装到pageBean对象里面，如果没有就不封装。
			 pageBean.setList(orderList);
		}
		
		//返回分页对象
		return pageBean;
	}
	
	/**
	 *   根据订单号删除该订单,并且还要将该订单相关的订单项，从订单项表中删除
	 *   
	 *   这里对方法含有delete*在配置xml里面配置好事务让spring容器管理,（自动开始关闭提交事务）
	 */
	
	@Override
	public void deleteThisOrderByOid(String oid) {
		
		
		//根据订单号先删除从表，订单项表里面该订单所有的订单项，然后再删除订单表里面该订单记录
		//(先删除从表orderItem)通过订单号oid,删除订单项表里面属于该订单的记录
		orderMapper.deleteOrderItemByOid(oid);
		
		//(再删除主表orders)
		orderMapper.deleteThisOrderByOid(oid);
		
		
	}

}
