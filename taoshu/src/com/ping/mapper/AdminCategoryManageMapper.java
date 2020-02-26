package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Category;


public interface AdminCategoryManageMapper {

	//如果用户输入分类名称作为查询就条件就查询相应的总条数，否则查询所有分类总条数
	int selectCategoryTotal(@Param("cname") String cname);
    //如果用户输入分类名称作为查询条件,模糊查询分类信息，并且是当前页，否则查询所有分类当前页信息
	List<Category> selectCategoryListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("cname") String cname);
	
	//添加分类时候检查分类名称是否存在
	Long checkCategoryIsExist(String cname);
	//添加分类信息，插入到数据库
	int addCategoryInfo(Category category);
	//编辑分类，通过获取cid查询分类信息进行回显
	Category selectCategoryInfoByCid(int cid);
	//编辑分类信息，通过cid将分类信息更新到数据库
	int updateCategoryInfo(Category category);
	//删除分类，通过cid进行批量删除
	int deleteCategoryInfo(int cid);

}
