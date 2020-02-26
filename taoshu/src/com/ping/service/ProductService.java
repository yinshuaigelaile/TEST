package com.ping.service;

import java.util.List;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Product;

/**
 * 商品信息，service层
 * @author admin
 *
 */
public interface ProductService {

	//查询最新商品信息，就是查询商品按时间进行降序，选择前几条
	List<Product> selectNewProductList();
    //查询热门商品信息
	List<Product> selectHotProductList();

	//根据商品id查询商品信息
	Product selectProductInfoById(int pid);
	
	//根据商品的分类id进行查询该类商品并且进行分页显示
	PageBean selectProductListByCid(int cid, int currentPage, int currentCount);
	
	//站内搜索，根据输入的商品名称进行模糊查询数据库
	List<Product> getProductNameListByInput(String inputvalue);
	//站内搜索，根据用户输入的信息进行分页查询商品信息
	PageBean selectProductInfoListByInput(String searchContent, int currentPage, int currentCount);
}
