package com.ping.common.utils;

import java.util.List;

import com.ping.pojo.Product;

/**
 * 定义一个实体类，对分页显示的数据进行封装
 * @author admin
 * @param <T>
 *
 */
//使用泛型，因为分页处了查询商品进行分页，订单或者其他也进行分页，泛型类型在创建对象时候进行确定
public class PageBean<T> {

	private int currentPage;//当前页数
	private int currentCount; //每页显示的条数
	private List<T> list;//每页显示的商品信息
	private int totalCount;//该类商品的总条数
	private int totalPage;//该类商品的总页数
	
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
