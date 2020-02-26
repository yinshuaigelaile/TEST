package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.mapper.CategoryMapper;
import com.ping.pojo.Category;

/**
 * ��ѯ������Ʒ�ķ�����Ϣ
 * @author admin
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryMapper categoryMapper;
	//��ѯ������Ʒ�ķ�����Ϣ
	@Override
	public List<Category> selectCategoryList() {
		//����dao�㷽�����в�ѯ��Ʒ������Ϣ
		List<Category> categoryList = categoryMapper.selectCategoryList();
		return categoryList;
	}

}
