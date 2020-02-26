package com.ping.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 订单表封装的类
 * 根据数据库的字段创建订单类，主要是封装数据库要存储的订单信息
 * @author admin
 *
 */
public class Order {

	/*CREATE TABLE `orders` (
	  `oid` int(11) NOT NULL AUTO_INCREMENT,
	  `ordertime` datetime DEFAULT NULL,
	  `total` double DEFAULT NULL,
	  `payment_type` int(2) DEFAULT NULL,
	  `status` int(11) DEFAULT NULL,
	  `receiver_name` varchar(20) DEFAULT NULL,
	  `receiver_phone` varchar(20) DEFAULT NULL,
	  `receiver_address` varchar(30) DEFAULT NULL,
	  `uid` int(11) DEFAULT NULL,
	  `payment_type` int(2) DEFAULT NULL,
	  
	  PRIMARY KEY (`oid`)
	)*/
	
	private String oid;           //订单id
	private String ordertime;    //订单下单时间
	private double total;      //订单的总价格
	private int payment_type;   //支付类型，1、货到付款，2、在线支付
	private int status;         //支付状态 1代表已付款，0代表未付款
	private String receiver_name; //收货人的姓名
	private String receiver_phone; //收货人的电话
	private String receiver_address; //收货人的地址
	//如果是外键，应该使用面向对象的思想，方便以后多表查询进行封装,只使用一个实体类就行（用户表和订单表是一对多关系）
	//private String uid;使用面向对象的思想，该字段是属于例外一个实体，直接引入该实体，这样可以拿到不单是uid，还有其他信息
	private User user;                //该订单属于哪个用户
	
	
	//订单表和订单项表是一对多关系，使用集合存储订单项信息
	//订单中有多少个订单项,封装这个主要是在传输数据，只需要传输order对象，它内部封装对应的订单项
	List<OrderItem> orderItems=new ArrayList<OrderItem>();
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(int payment_type) {
		this.payment_type = payment_type;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getReceiver_address() {
		return receiver_address;
	}
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
