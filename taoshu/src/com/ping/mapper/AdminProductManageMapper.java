package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Category;
import com.ping.pojo.Product;

/**
 * 后台商品管理，包括新增商品，编辑商品，删除商品，查询商品，上架、下架
 * @author admin
 *
 */
public interface AdminProductManageMapper {

	//查询所有商品的总记录，如果用户输入商品名称不为空就根据商品名称模糊查询出相关商品的总数
	int selectProductTotal(@Param("pname") String pname);
    //根据商品名称和当前页查询该商品名称中当前页的商品信息
	List<Product> selectProductListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("pname") String pname);
	
	//在添加商品时候先查询所有分类信息进行回显供用户选择
	List<Category> selectAllCategoryInfo();
	//添加商品信息
	int addProductInfo(Product product);
	//编辑商品，通过pid查询该条商品信息，进行回显
	Product selectProductInfoByPid(int pid);
	//通过商品的pid更新商品的信息
	int editProductInfo(Product product);
	//删除商品信息，通过pid删除商品信息
	int deleteProductInfo(int pid);
	//商品上架，通过pid更新商品信息让pflag=1表示商品上架
	int updatePublishProductInfoByPid(int pid);
	//商品下架，通过pid更新商品信息让pflag=0表示商品下架
	int updateUnpublishProductInfoByPid(int pid);

}
