package com.ping.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * �������װ����
 * �������ݿ���ֶδ��������࣬��Ҫ�Ƿ�װ���ݿ�Ҫ�洢�Ķ�����Ϣ
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
	
	private String oid;           //����id
	private String ordertime;    //�����µ�ʱ��
	private double total;      //�������ܼ۸�
	private int payment_type;   //֧�����ͣ�1���������2������֧��
	private int status;         //֧��״̬ 1�����Ѹ��0����δ����
	private String receiver_name; //�ջ��˵�����
	private String receiver_phone; //�ջ��˵ĵ绰
	private String receiver_address; //�ջ��˵ĵ�ַ
	//����������Ӧ��ʹ����������˼�룬�����Ժ����ѯ���з�װ,ֻʹ��һ��ʵ������У��û���Ͷ�������һ�Զ��ϵ��
	//private String uid;ʹ����������˼�룬���ֶ�����������һ��ʵ�壬ֱ�������ʵ�壬���������õ�������uid������������Ϣ
	private User user;                //�ö��������ĸ��û�
	
	
	//������Ͷ��������һ�Զ��ϵ��ʹ�ü��ϴ洢��������Ϣ
	//�������ж��ٸ�������,��װ�����Ҫ���ڴ������ݣ�ֻ��Ҫ����order�������ڲ���װ��Ӧ�Ķ�����
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
