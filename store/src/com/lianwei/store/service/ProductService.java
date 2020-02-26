package com.lianwei.store.service;

import java.util.List;

import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.PageModel1;
import com.lianwei.store.domain.Product;

public interface ProductService {

	List<Product> getHotProducts() throws Exception;

	List<Product> getNewProducts() throws Exception;

	Product findProductByPid(String pid) throws Exception;

//	PageModel findProductsByCidWithPage(String cid, int num) throws Exception;
	PageModel1 findProductsByCidWithPage(String cid, int num) throws Exception;

}
