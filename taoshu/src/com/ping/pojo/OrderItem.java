package com.ping.pojo;
/**
 * �������װÿһ�����Ʒ
 * 
 * ��Ʒ��Ͷ��������һ�Զࣨһ����Ʒ���Է��ڲ�ͬ�Ķ��������ÿһ��������ֻ��ĳһ����Ʒ����������������Ϊ�����
 * ������Ͷ������Ҳ��һ�Զࣨһ�����������ж�����������ÿ��������ֻ����ĳһ��������
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
	
	private int itemid;     //������id
	private int count;      //�ö���������Ʒ�Ĺ�������
	private double subtotal;  //������С��
	//�����������һ�����ʹ���������˼·��װ�ɶ��󣬿��Դ洢����������Ϣ
	//�������˼��һ���������һ�����������ͨ�����������
	private Product product; //�ö�������Ʒ��Ϣ
	private Order order;     //�ö����������Ķ���
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
