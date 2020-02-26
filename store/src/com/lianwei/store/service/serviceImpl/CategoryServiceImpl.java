package com.lianwei.store.service.serviceImpl;

import java.util.List;

import com.lianwei.store.dao.CategoryDao;
import com.lianwei.store.dao.daoImpl.CategoryDaoImpl;
import com.lianwei.store.domain.Category;
import com.lianwei.store.service.CategoryService;


public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> getAllCategories() throws Exception {
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.getAllCategories();
	}

}
