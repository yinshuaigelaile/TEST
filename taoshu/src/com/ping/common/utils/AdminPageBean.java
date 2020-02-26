package com.ping.common.utils;

import java.util.List;

/**
 * 后台分页类，只需要封装easyUI需要返回的total总页数和rows当前页显示的内容
 * 使用easyUI作为前台框架之后，前台分页提交的当前页和每页显示的条数变量名已经定死分别为 page,rows
 * 后台查询返回的json数据格式也是定死，返回total总页数，rows当前页显示的内容名称也是不能变
 * @author admin
 *
 */
public class AdminPageBean<T> {
	
    //使用easyUI框架只需要返回total和rows
	private int total; //总条数  easyUI已经确定该变量名为了能使前台填充数据表格数据这里该变量名不能改变
	private List<T> rows;//当前页显示的内容 easyUI已经确定该变量名为了能使前台填充数据表格数据这里该变量名不能改变
	//总页数这里就不用自己计算，前台easyUI帮我们算
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	

}
