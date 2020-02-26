package com.lianwei.store.service.serviceImpl;

import java.util.List;

import com.lianwei.store.dao.AdminProductDao;
import com.lianwei.store.dao.daoImpl.AdminProductDaoImpl;
import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.Product;
import com.lianwei.store.service.AdminProductService;

public class AdminProductServiceImpl implements AdminProductService {

	@Override
	public PageModel findAllProducts(int num) throws Exception {
		AdminProductDao adminProductDao = new AdminProductDaoImpl();
		int toalRecodes = adminProductDao.getTotalCount();
		PageModel pageModel = new PageModel(num, toalRecodes,10);
		//select * from limit start,num
		List<Product> list = adminProductDao.findAllProducts(pageModel.getStartIndex(),pageModel.getPageSize());
		pageModel.setRecords(list);
		pageModel.setUrl("/AdminProductServlet?method=findAllProducts");
		return pageModel;
	}

	@Override
	public PageModel findAllPushDownProducts(int num) throws Exception {
		AdminProductDao adminProductDao = new AdminProductDaoImpl();
		int toalRecodes = adminProductDao.getTotalPushDownCount();
		PageModel pageModel = new PageModel(num, toalRecodes,10);
		//select * from limit start,num
		List<Product> list = null;
		if (toalRecodes!=0) {
			list = adminProductDao.findAllProducts(pageModel.getStartIndex(),pageModel.getPageSize());
		}
		pageModel.setRecords(list);
		pageModel.setUrl("/AdminProductServlet?method=findAllPushDownProducts");
		return pageModel;
	}

	@Override
	public void addProduct(Product product)  throws Exception{
		AdminProductDao adminProductDao = new AdminProductDaoImpl();
		adminProductDao.addProduct(product);
	}

}
