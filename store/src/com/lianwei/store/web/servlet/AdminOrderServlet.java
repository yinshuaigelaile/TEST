package com.lianwei.store.web.servlet;



import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianwei.store.domain.Order;
import com.lianwei.store.service.AdminOrderService;
import com.lianwei.store.service.OrderService;
import com.lianwei.store.service.serviceImpl.AdminOrderServiceImpl;
import com.lianwei.store.service.serviceImpl.OrderServiceImpl;
import com.lianwei.store.web.base.BaseServlet;

import net.sf.json.JSONArray;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	//查询所有订单信息
	public String findAllOrder(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		AdminOrderService adminOrderService = new AdminOrderServiceImpl();
		String state = req.getParameter("state");
		List<Order> list = null;
		if(null==state||"".equals(state)) {
			list = adminOrderService.findAllOrder();
		}else {
			list = adminOrderService.findStateOrder(state);
		}
		System.out.println(list);
		req.setAttribute("list", list);
		return "/admin/order/list1.jsp";
	}
	//根据订单id查询所有的订单项信息
	public String findOrderItemByOidWithAjax(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		String oid = req.getParameter("oid");
		//根据oid获取对应的所有订单信息
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		//将对应的所有订单信息转换为json格式
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		//告诉浏览器本次响应地数据时JSON格式地字符串
		resp.setContentType("application/json;charset=utf-8");
		//将json数据写入到页面去
		resp.getWriter().print(jsonStr);
		return null;
	}
	
	//更新订单状态信息
	public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		String oid = req.getParameter("oid");
		//修改状态
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		order.setState(3);
		orderService.updateOrder(order);
		//重定向到已发货订单页面
		resp.sendRedirect("/store/AdminOrderServlet?method=findAllOrder&state=3");
		return null;	
	}
	

}
