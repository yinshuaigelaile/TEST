package com.lianwei.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	//属性  购物项 各类 购物商品信息
	Map<String,CartItem> map = new HashMap<String,CartItem>();
	// 商品金额 赠送积分
	private double totalPrice=0;
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public double getTotalPrice() {
		//向让总计请0
		totalPrice=0;
		Collection<CartItem> cartItems= map.values();
		for (CartItem cartItem : cartItems) {
			totalPrice+=cartItem.getTotalNum();
		}
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	//加入购物车方法
	public void addCartItemToCart(CartItem cartItem) {
		String pid = cartItem.getProduct().getPid();
		//判断购物项在购物车是否存在,通过购物车中的商品pid来进行判断
		if (map.containsKey(pid)) {
			CartItem oldCartItem = map.get(pid);
			oldCartItem.setNum(oldCartItem.getNum()+cartItem.getNum());
		}else {
			map.put(pid,cartItem);
		}
	}
	//清空购物车
	public void clearCart() {
		map.clear();
	}
	//清空购物车中购物项方法
	public void clearCartItemFromCart(String pid) {
		map.remove(pid);
	}
}
