package com.ping.mapper;

import java.util.List;

import com.ping.pojo.Category;

/**
 * ��Ʒ������ʾ
 * @author admin
 *
 */
public interface CategoryMapper {

	/**
	 * ��ѯ�������������Ʒ������Ϣ
	 * 
	 */
	public List<Category> selectCategoryList();
}
