package com.ping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * ��ʾ���ﳵ���洢���ﳵ��ÿһ����󣩣�ʹ�ü������洢�����ϴ洢����
 * @author admin
 *
 */
public class Cart {

	//�洢���������,Ϊ�˺��淽��ɾ��ĳһ����Ϣ���������ʹ��Map���ϴ洢����Ʒ��Ψһ��ʶ
	//�ڴ������϶���ʱ������ʼ������Ȼ����һ���ն��󣬲��ܴ洢����
	private Map<Integer,CartItem> cartMap=new HashMap<Integer,CartItem>();  //�洢���ﳵ���map����
	//����һ���洢���й�������ܽ��
	private double total;    //���ﳵ��������Ʒ���ܽ��
	
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
