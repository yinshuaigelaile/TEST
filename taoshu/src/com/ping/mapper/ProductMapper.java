package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Product;

/**
 * 商品信息Dao
 * @author admin
 *
 */
public interface ProductMapper {

	//查询最新商品信息
	public List<Product> selectNewProductList();

	//查询热门商品信息
	public List<Product> selectHotProductList();
	
	//通过商品id查询商品信息
	public Product selectProductInfoById(int pid);

	//根据商品分类cid查询该类商品的总记录数
	public Long selectTotalCount(int cid);
	
    //根据商品分类cid,还有当前页，每页显示多少条，查询该类商品的信息
	public List<Product> selectCurrentPageProductList(@Param("cid") int cid,@Param("currentStart") int currentStart,@Param("currentCount") int currentCount);

	//站内搜索，根据输入的商品名称查询数据库
	public List<Product> getProductNameListByInput(String inputvalue);

	//站内搜索，根据商品名称模糊查询总记录数
	public Long selectTotalCountByName(String searchContent);

	//根据商品名称模糊分页查询,mybatis传多个参数使用@Param()注解指定的名称
	public List<Product> selectCurrentPageProductListByName(@Param("searchContent") String searchContent,@Param("currentStart") int currentStart,@Param("currentCount") int currentCount);
}
