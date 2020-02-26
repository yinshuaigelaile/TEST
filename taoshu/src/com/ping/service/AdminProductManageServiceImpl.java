package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminProductManageMapper;
import com.ping.pojo.Category;
import com.ping.pojo.Product;
/**
 * 后台商品管理，包括新增商品，编辑商品，删除商品，查询商品，上架、下架
 * @author admin
 *
 */
@Service
public class AdminProductManageServiceImpl implements AdminProductManageService {
	
	@Autowired
	private AdminProductManageMapper adminProductManageMapper;
	
	//通过商品名称进行模糊查询分页显示，如果没有输入条件就查询所有商品信息并且分页显示
	@Override
	public AdminPageBean getProductListPageBean(int currentPage, int pageSize, String pname) {
		//主要任何是创建分页对象并封装如下数据,创建分页对象需要确定泛型的类型
		AdminPageBean<Product> pageBean=new AdminPageBean<Product>();
		//1.查询分页对象属性total总记录数并封装
		int total=adminProductManageMapper.selectProductTotal(pname);
		//封装到分页对象
		pageBean.setTotal(total);
		//2.查询分页对象属性rows当前页的的内容并封装
		//分页查询mysql数据库sql语句需要确定limit ？，？两个参数，第一个当前页开始位置（需要计算），第二个参数是每页显示的条数pageSize
		int currentStart=(currentPage-1)*pageSize;//表示当前页开始位置索引
		//调用dao查询当前页内容
		List<Product> productList=adminProductManageMapper.selectProductListInfo(currentStart,pageSize,pname);
		//封装分页对象当前页内容
		pageBean.setRows(productList);
		//3.返回分页对象
		return pageBean;
		
	}
    //在添加商品信息时候，先查询所有分类信息进行回显供用户选择
	@Override
	public List<Category> selectAllCategoryInfo() {
		//查询所有分类信息
		List<Category> categoryList=adminProductManageMapper.selectAllCategoryInfo();
		return categoryList;
	}
	//添加商品信息
	@Override
	public int addProductInfo(Product product) {
		int row=adminProductManageMapper.addProductInfo(product);
		return row;
	}
	//编辑商品，通过商品的pid进行查询商品信息进行回显
	@Override
	public Product selectProductInfoByPid(int pid) {
		//通过商品的pid查询该条商品的信息
		Product product=adminProductManageMapper.selectProductInfoByPid(pid);
		//返回封装好的商品对象
		return product;
	}
	
	//编辑商品，将封装好的商品信息更新到数据库
	@Override
	public int editProductInfo(Product product) {
		int row=adminProductManageMapper.editProductInfo(product);
		return row;
	}
	
	//删除商品信息，通过商品的pid进行批量删除商品信息
	@Override
	public int deleteProductInfo(String[] arrayPid) {
		int row=0;
	  	//遍历数组获取数组中每个pid
		for(int i=0;i<arrayPid.length;i++)
		{
			//得到每个商品的pid，调用dao层进行删除
			String strPid=arrayPid[i];
			//将字符串类型pid转换成int类型
			int pid = Integer.parseInt(strPid);
			//将pid传输到dao层,进行删除商品信息
			row=adminProductManageMapper.deleteProductInfo(pid);
			
		}
		//如果删除成功row其实为1
		return row;
		
	}
	//商品上架，通过pid进行批量更新，设置商品的pflag=1表示上架
	@Override
	public int updatePublishProductInfoByPid(String[] arrayPid) {
		int row=0;
	  	//遍历数组获取数组中每个pid
		for(int i=0;i<arrayPid.length;i++)
		{
			//得到每个商品的pid，调用dao层进行更新让pflag=1
			String strPid=arrayPid[i];
			//将字符串类型pid转换成int类型
			int pid = Integer.parseInt(strPid);
			//将pid传输到dao层,进行更新商品信息
			row=adminProductManageMapper.updatePublishProductInfoByPid(pid);
			
		}
		//如果更新成功row其实为1
		return row;
	}
	
	//商品下架，通过pid进行批量更新，设置商品的pflag=0表示下架
	@Override
	public int updateUnpublishProductInfoByPid(String[] arrayPid) {
		int row=0;
	  	//遍历数组获取数组中每个pid
		for(int i=0;i<arrayPid.length;i++)
		{
			//得到每个商品的pid，调用dao层进行更新让pflag=0
			String strPid=arrayPid[i];
			//将字符串类型pid转换成int类型
			int pid = Integer.parseInt(strPid);
			//将pid传输到dao层,进行更新商品信息
			row=adminProductManageMapper.updateUnpublishProductInfoByPid(pid);
			
		}
		//如果更新成功row其实为1
		return row;
	}
	
	
	

}
