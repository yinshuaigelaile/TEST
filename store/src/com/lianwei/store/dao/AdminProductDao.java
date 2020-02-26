package com.lianwei.store.dao;

import java.util.List;

import com.lianwei.store.domain.Product;

public interface AdminProductDao {

	int getTotalCount() throws Exception;

	List<Product> findAllProducts(int startIndex, int pageSize) throws Exception;
	
	public int getTotalPushDownCount() throws Exception;

	void addProduct(Product product) throws Exception;

}
