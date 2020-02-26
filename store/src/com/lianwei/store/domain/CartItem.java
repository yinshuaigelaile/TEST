package com.lianwei.store.domain;

public class CartItem {
	private Product product;
	private int num;
	private double totalNum;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getTotalNum() {
		return num*product.getShop_price();
	}
	public void setTotalNum(double totalNum) {
		this.totalNum = totalNum;
	}
	
	
}