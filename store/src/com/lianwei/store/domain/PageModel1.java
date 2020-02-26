package com.lianwei.store.domain;

import java.util.List;

public class PageModel1 {
	//select * from product limit startIndex,pageSize
	//select * from  product limit (currentPage-1)*pageSize,pageSize
	/**
	 * @param
	 * currentPage 当前页
	 * pageSize  每页大小，由用户指定，或者固定死
	 * prePageNum  上一页
	 * nextPageNum  下一页
	 * totalRecodeNum  总记录数
	 * startIndex   当前开始记录
	 * startNum   开始页
	 * endNum   结束页
	 * List records
	 */
	
	private int currentPage;
	private int pageSize = 5;
	private int prePageNum;
	private int nextPageNum;
	private int totalRecodeNum;
	private int totalPageNum;
	private int startIndex;
	private int startNum;
	private int endNum;
	
	
	private List records;

	private String url;
	public PageModel1() {
		
	}

	/**
	 * 构造方法初始化
	 * @param currentPage
	 * @param totalRecodeNum
	 * @param pageSize
	 */
	public PageModel1(int currentPage, int totalRecodeNum,int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRecodeNum = totalRecodeNum;
		startIndex = (currentPage-1)*pageSize;
		totalPageNum = totalRecodeNum%pageSize==0?totalRecodeNum/pageSize:(totalRecodeNum/pageSize+1);
		
		startNum = currentPage-4;
		endNum = currentPage+4;
		
		//超过9页
		if (totalPageNum>9) {
			if (startNum<1) {
				startNum=1;
				endNum=startNum+8;
			}
			if (endNum>totalPageNum) {
				endNum=totalPageNum;
				startNum=endNum-8;
			}
		}else {
			startNum = 1;
			endNum = totalPageNum;
		}
		
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPrePageNum() {
		prePageNum = currentPage-1;
		if (prePageNum<1) {
			prePageNum=1;
		}
		return prePageNum;
	}


	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}


	public int getNextPageNum() {
		nextPageNum = nextPageNum+1;
		if (nextPageNum>totalPageNum) {
			nextPageNum=totalPageNum;		
		}
		return nextPageNum;
	}


	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}


	public int getTotalRecodeNum() {
		return totalRecodeNum;
	}


	public void setTotalRecodeNum(int totalRecodeNum) {
		this.totalRecodeNum = totalRecodeNum;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


	public int getStartNum() {
		return startNum;
	}


	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}


	public int getEndNum() {
		return endNum;
	}


	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "PageModel1 [currentPage=" + currentPage + ", pageSize=" + pageSize + ", prePageNum=" + prePageNum
				+ ", nextPageNum=" + nextPageNum + ", totalRecodeNum=" + totalRecodeNum + ", startIndex=" + startIndex
				+ ", startNum=" + startNum + ", endNum=" + endNum + ", records=" + records + "]";
	}
	
	
}
