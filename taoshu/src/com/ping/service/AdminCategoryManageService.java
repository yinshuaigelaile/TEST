package com.ping.service;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;

public interface AdminCategoryManageService {

	//通过分类名称进行模糊查询分页显示，如果没有输入条件就查询所有分类信息
	AdminPageBean getCategoryListPageBean(int currentPage, int pageSize, String cname);

	//在添加分类时候，检查该分类名称是否存在
	boolean checkCategoryIsExist(String cname);
    //添加分类信息，并返回mybatis插入到数据库影响数据库的行数判断是否插入成功或失败
	int addCategoryInfo(Category category);
    //编辑分类，通过获取到的cid查询该分类的信息，进行回显
	Category selectCategoryInfoByCid(int cid);
    //编辑分类，通过cid将分类信息更新到数据库
	int updateCategoryInfo(Category category);
    //删除分类，通过cid进行批量删除
	int deleteCategoryInfo(String[] arrayCid);

}
