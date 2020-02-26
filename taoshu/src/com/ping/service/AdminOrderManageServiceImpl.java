package com.ping.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminOrderManageMapper;
import com.ping.pojo.Category;
import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;

/**
 * 后台订单管理
 * @author admin
 *
 */
@Service
public class AdminOrderManageServiceImpl implements AdminOrderManageService {

	@Autowired
	private AdminOrderManageMapper adminOrderManageMapper;
	
	//默认查询所有订单信息中当前页的订单信息，如果输入条件订单号就查询该订单号订单信息
	@Override
	public AdminPageBean getOrderListPageBean(int currentPage, int pageSize, String oid) {
		//主要任何是创建分页对象并封装如下数据,创建分页对象需要确定泛型的类型
		AdminPageBean<Order> pageBean=new AdminPageBean<Order>();
		//1.查询分页对象属性total总记录数并封装
		int total=adminOrderManageMapper.selectOrderTotal(oid);
		//封装到分页对象
		pageBean.setTotal(total);
		//2.查询分页对象属性rows当前页的的内容并封装
		//分页查询mysql数据库sql语句需要确定limit ？，？两个参数，第一个当前页开始位置（需要计算），第二个参数是每页显示的条数pageSize
		int currentStart=(currentPage-1)*pageSize;//表示当前页开始位置索引
		//调用dao查询当前页内容
		List<Order> orderList=adminOrderManageMapper.selectOrderListInfo(currentStart,pageSize,oid);
		//由于mysql中的datetime时间类型会在秒后面出现.0现在需要格式化一下时间
		for(Order order:orderList)
		{
			
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
	   
		//封装分页对象当前页内容
		pageBean.setRows(orderList);
		//3.返回分页对象
		return pageBean;
	}

	//通过订单号查询该订单所有订单项信息，涉及到多表查询，和分页查询
	@Override
	public AdminPageBean getOrderItemListPageBean(int currentPage, int pageSize, String oid) {
		//主要任何是创建分页对象并封装如下数据,创建分页对象需要确定泛型的类型
		AdminPageBean<OrderItem> pageBean=new AdminPageBean<OrderItem>();
		//1.查询分页对象属性total总记录数并封装
		int total=adminOrderManageMapper.selectOrderItemTotal(oid);
		//封装到分页对象
		pageBean.setTotal(total);
		//2.查询分页对象属性rows当前页的的内容并封装
		//分页查询mysql数据库sql语句需要确定limit ？，？两个参数，第一个当前页开始位置（需要计算），第二个参数是每页显示的条数pageSize
		int currentStart=(currentPage-1)*pageSize;//表示当前页开始位置索引
		//调用dao查询当前页内容
		List<OrderItem> orderItemList=adminOrderManageMapper.selectOrderItemListInfo(currentStart,pageSize,oid);
		//封装分页对象当前页内容
		pageBean.setRows(orderItemList);
		//3.返回分页对象
		return pageBean;
	}

}
