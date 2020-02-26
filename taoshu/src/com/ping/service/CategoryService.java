package com.ping.service;

import java.util.List;

import com.ping.pojo.Category;
import com.ping.pojo.Product;

public interface CategoryService {

	//显示商品分类信息
	public List<Category> selectCategoryList();
	
  
}
