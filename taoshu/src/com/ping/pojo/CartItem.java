package com.ping.pojo;
/**
 * 该类主要是封装每一项商品信息，就是购物车项，一个购物车包括很多项商品
 * @author admin
 *
 */
public class CartItem {

	private Product product;//封装商品基本信息
	private int buyNum;//购买的数量
	private double subTotal;//小计，一种商品的总价格
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
}
