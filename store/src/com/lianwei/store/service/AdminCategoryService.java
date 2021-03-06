package com.lianwei.store.service;

import java.util.List;

import com.lianwei.store.domain.Category;

public interface AdminCategoryService {
	 List<Category> findAllCats() throws Exception;

	void addCategory(Category category) throws Exception ;

	Category findCatByCid(String cid) throws Exception;
	
	void editCategory(Category category) throws Exception ;

	void delCategory(Category category) throws Exception ;
}
