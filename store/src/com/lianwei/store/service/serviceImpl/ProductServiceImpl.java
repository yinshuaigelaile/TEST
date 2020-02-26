package com.lianwei.store.service.serviceImpl;

import java.util.List;

import com.lianwei.store.dao.ProductDao;
import com.lianwei.store.dao.daoImpl.ProductDaoImpl;
import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.PageModel1;
import com.lianwei.store.domain.Product;
import com.lianwei.store.service.ProductService;

public class ProductServiceImpl implements ProductService {
	ProductDao productDao = new ProductDaoImpl();
	@Override
	public List<Product> getHotProducts() throws Exception {
		return productDao.getHotProducts();
	}

	@Override
	public List<Product> getNewProducts() throws Exception {
		return productDao.getNewProducts();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception{
		return productDao.findProductByPid(pid);
	}

	
	@Override
	public PageModel1 findProductsByCidWithPage(String cid, int num) throws Exception {
		//根据cid去查询出对应的记录数  num为当前页数
		int totalRecords = productDao.getTotalRecordsByCid(cid);
		//创建分类的类   PageModel   
		PageModel1 pageModel = new PageModel1(num, totalRecords, 12);
		//sql语句  select * from product where cid = ? limit startIndex,num
		List<Product> list = productDao.findProductsByCidWithPage(cid,pageModel.getStartIndex(),pageModel.getPageSize());
//		pageModel.setRecords(list);
		pageModel.setRecords(list);
		pageModel.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
		return pageModel;
	}

}
