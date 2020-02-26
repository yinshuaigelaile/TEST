package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.mapper.CategoryMapper;
import com.ping.pojo.Category;

/**
 * 查询所有商品的分类信息
 * @author admin
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryMapper categoryMapper;
	//查询所有商品的分类信息
	@Override
	public List<Category> selectCategoryList() {
		//调用dao层方法进行查询商品分类信息
		List<Category> categoryList = categoryMapper.selectCategoryList();
		return categoryList;
	}

}
