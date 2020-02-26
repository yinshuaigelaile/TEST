package com.ping.pojo;
/**
 * 订单项，封装每一项订单商品
 * 
 * 商品表和订单项表是一对多（一种商品可以放在不同的订单项，但是每一个订单项只能某一种商品，将主机加入多表作为外键）
 * 订单表和订单项表也是一对多（一个订单可以有多个订单项，但是每个订单项只属于某一个订单）
 * @author admin
 *
 */
public class OrderItem {
	
	/*CREATE TABLE `orderitem` (
	  `itemid` int(11) NOT NULL AUTO_INCREMENT,
	  `count` int(11) DEFAULT NULL,
	  `subtotal` double DEFAULT NULL,
	  `pid` int(11) DEFAULT NULL,
	  `oid` int(11) DEFAULT NULL,
	  PRIMARY KEY (`itemid`),*/
	
	private int itemid;     //订单项id
	private int count;      //该订单项内商品的购买数量
	private double subtotal;  //订单项小计
	//下面是外键，一般外键使用面向对象思路封装成对象，可以存储更多其他信息
	//面向对象思想一个表包含另一个表的主键，通过对象来表达
	private Product product; //该订单项商品信息
	private Order order;     //该订单项所属的订单
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
			 
}
