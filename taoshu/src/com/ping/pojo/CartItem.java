package com.ping.pojo;
/**
 * ������Ҫ�Ƿ�װÿһ����Ʒ��Ϣ�����ǹ��ﳵ�һ�����ﳵ�����ܶ�����Ʒ
 * @author admin
 *
 */
public class CartItem {

	private Product product;//��װ��Ʒ������Ϣ
	private int buyNum;//���������
	private double subTotal;//С�ƣ�һ����Ʒ���ܼ۸�
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
