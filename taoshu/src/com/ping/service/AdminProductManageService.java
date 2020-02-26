package com.ping.service;

import java.util.List;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;
import com.ping.pojo.Product;

/**
 * 后台商品管理，包括新增商品，编辑商品，删除商品，查询商品，上架、下架
 * @author admin
 *
 */
public interface AdminProductManageService {
	//通过商品名称进行模糊查询分页显示，如果没有输入条件就查询所有商品信息
	AdminPageBean getProductListPageBean(int currentPage, int pageSize, String pname);
    //在添加商品时候先查询所有分类信息进行回显供用户选择
	List<Category> selectAllCategoryInfo();
	//添加商品信息
	int addProductInfo(Product product);
	//编辑商品，通过pid进行查询商品信息回显
	Product selectProductInfoByPid(int pid);
	//编辑商品，将封装好的商品信息更新到数据库
	int editProductInfo(Product product);
	//删除商品信息，根据商品的pid进行批量删除商品信息
	int deleteProductInfo(String[] arrayPid);
	//商品上架，通过商品pid进行批量更新商品信息设置pflag=1
	int updatePublishProductInfoByPid(String[] arrayPid);
	//商品下架，通过商品pid进行批量更新商品信息设置pflag=0
	int updateUnpublishProductInfoByPid(String[] arrayPid);

}
