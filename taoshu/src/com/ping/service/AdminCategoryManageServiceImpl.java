package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminCategoryManageMapper;
import com.ping.pojo.Category;
import com.ping.pojo.User;

@Service
public class AdminCategoryManageServiceImpl implements AdminCategoryManageService {

	@Autowired
	private AdminCategoryManageMapper adminCategoryManageMapper;
	
	//通过分类名称进行模糊查询分页显示，如果没有输入条件就查询所有分类信息
	@Override
	public AdminPageBean getCategoryListPageBean(int currentPage, int pageSize, String cname) {
		//主要任何是创建分页对象并封装如下数据,创建分页对象需要确定泛型的类型
		AdminPageBean<Category> pageBean=new AdminPageBean<Category>();
		//1.查询分页对象属性total总记录数并封装
		int total=adminCategoryManageMapper.selectCategoryTotal(cname);
		//封装到分页对象
		pageBean.setTotal(total);
		//2.查询分页对象属性rows当前页的的内容并封装
		//分页查询mysql数据库sql语句需要确定limit ？，？两个参数，第一个当前页开始位置（需要计算），第二个参数是每页显示的条数pageSize
		int currentStart=(currentPage-1)*pageSize;//表示当前页开始位置索引
		//调用dao查询当前页内容
		List<Category> categoryList=adminCategoryManageMapper.selectCategoryListInfo(currentStart,pageSize,cname);
		//封装分页对象当前页内容
		pageBean.setRows(categoryList);
		//3.返回分页对象
		return pageBean;
	}

	//在添加分类时候检查该分类名称是否存在
	@Override
	public boolean checkCategoryIsExist(String cname) {
		//通过分类名称查询该分类是否存在
		Long checkUserIsExsit=adminCategoryManageMapper.checkCategoryIsExist(cname);
		//checkUserIsExsit>0代表分类名称存在，否则不存在
		return checkUserIsExsit>0?true:false;
	}
    
	//添加分类信息
	@Override
	public int addCategoryInfo(Category category) {
		//将分类信息传输到dao层将该信息插入到数据库
		int row=adminCategoryManageMapper.addCategoryInfo(category);
		return row;

	}
	//编辑用户，通过获取到的cid查询该分类的信息，进行回显
	@Override
	public Category selectCategoryInfoByCid(int cid) {
		//调用dao层方法，通过cid查询该分类的信息
		Category category=adminCategoryManageMapper.selectCategoryInfoByCid(cid);
		return category;
	}
    //编辑分类信息，通过cid将分类信息更新到数据库
	@Override
	public int updateCategoryInfo(Category category) {
		//mybatis更新数据库如果成功会返回一个int类型的值，使用row接收如果row=1>0表示更新成功，否则更新失败
		int row=adminCategoryManageMapper.updateCategoryInfo(category);
		return row;
	}
    //删除分类，通过cid进行批量删除
	@Override
	public int deleteCategoryInfo(String[] arrayCid) {
		int row=0;
	  	//遍历数组获取数组中每个cid
		for(int i=0;i<arrayCid.length;i++)
		{
			//得到每个用户的cid，调用dao层进行删除
			String strCid=arrayCid[i];
			//将字符串类型cid转换成int类型
			int cid = Integer.parseInt(strCid);
			//将cid传输到dao层
			row=adminCategoryManageMapper.deleteCategoryInfo(cid);
			
		}
		//如果删除成功row其实为1
		return row;
		
	}
	

}
