package com.lianwei.store.service;

import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.Product;

public interface AdminProductService {

	PageModel findAllProducts(int num) throws Exception;

	PageModel findAllPushDownProducts(int num) throws Exception;

	void addProduct(Product product) throws Exception;

}
