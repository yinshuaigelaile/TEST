package com.lianwei.store.dao;

import java.util.List;

import com.lianwei.store.domain.Product;

public interface ProductDao {

	List<Product> getHotProducts() throws Exception;

	List<Product> getNewProducts() throws Exception;

	Product findProductByPid(String pid) throws Exception;

	int getTotalRecordsByCid(String cid) throws Exception ;

	List<Product> findProductsByCidWithPage(String cid,int startIndex, int pageSize)  throws Exception ;
	

	public void insert(Product product) throws Exception;
}
