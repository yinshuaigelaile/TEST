package com.ping.common.utils;

import java.util.List;

import com.ping.pojo.Product;

/**
 * ����һ��ʵ���࣬�Է�ҳ��ʾ�����ݽ��з�װ
 * @author admin
 * @param <T>
 *
 */
//ʹ�÷��ͣ���Ϊ��ҳ���˲�ѯ��Ʒ���з�ҳ��������������Ҳ���з�ҳ�����������ڴ�������ʱ�����ȷ��
public class PageBean<T> {

	private int currentPage;//��ǰҳ��
	private int currentCount; //ÿҳ��ʾ������
	private List<T> list;//ÿҳ��ʾ����Ʒ��Ϣ
	private int totalCount;//������Ʒ��������
	private int totalPage;//������Ʒ����ҳ��
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	
    
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
