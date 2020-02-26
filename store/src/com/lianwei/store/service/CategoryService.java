package com.lianwei.store.service;

import java.util.List;

import com.lianwei.store.domain.Category;

public interface CategoryService {

	List<Category> getAllCategories() throws Exception;

}
