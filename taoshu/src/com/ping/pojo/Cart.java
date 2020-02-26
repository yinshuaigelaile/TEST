package com.ping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 表示购物车，存储购物车的每一项（对象），使用集合来存储（集合存储对象）
 * @author admin
 *
 */
public class Cart {

	//存储购物项对象,为了后面方便删除某一条信息，这里最好使用Map集合存储该商品的唯一标识
	//在创建集合对象时候必须初始化，不然就是一个空对象，不能存储数据
	private Map<Integer,CartItem> cartMap=new HashMap<Integer,CartItem>();  //存储购物车项的map集合
	//定义一个存储所有购物项的总金额
	private double total;    //购物车中所有商品的总金额
	
	public Map<Integer, CartItem> getCartMap() {
		return cartMap;
	}
	public void setCartMap(Map<Integer, CartItem> cartMap) {
		this.cartMap = cartMap;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
