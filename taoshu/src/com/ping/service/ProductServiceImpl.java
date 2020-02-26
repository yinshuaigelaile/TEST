package com.ping.service;
/**
 * 商品信息service层
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.PageBean;
import com.ping.mapper.ProductMapper;
import com.ping.pojo.Product;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	//查询最新商品信息列表
	@Override
	public List<Product> selectNewProductList() {
		//调用dao层方法进行查询
		List<Product> newProductList = productMapper.selectNewProductList();
		return newProductList;
	}
	//查询热门商品信息
	@Override
	public List<Product> selectHotProductList() {
		//调用dao层方法查询热门商品信息
		List<Product> hotProductList=productMapper.selectHotProductList();
		return hotProductList;
	}
	//通过商品id查询商品信息
	@Override
	public Product selectProductInfoById(int pid) {
		//根据商品id查询商品信息
		Product productInfo = productMapper.selectProductInfoById(pid);
		return productInfo;
	}
	
	//根据商品的分类id查询该类商品信息并且进行分页显示
	@Override
	public PageBean selectProductListByCid(int cid, int currentPage, int currentCount) {
		//对前台需要的数据进行查询，并且进行封装,
		//应该分页类是使用泛型，在创建对象时候确定类型
		PageBean<Product> pageBean=new PageBean<Product>();
		//设置当前页
		pageBean.setCurrentPage(currentPage);
		//设置每页显示的条数
		pageBean.setCurrentCount(currentCount);
		//设置总记录数，需要根据商品分类cid从数据库中查询该类商品的总记录数
		int totalCount=productMapper.selectTotalCount(cid).intValue();
		pageBean.setTotalCount(totalCount);
		//设置总页数，通过每页显示多少条，还有总条数，计算出一共有多少页
		int totalPage=(totalCount+currentCount-1)/currentCount;
		pageBean.setTotalPage(totalPage);
		//计算数据库limit第一个参数值，通过当前页和每页显示条数
		/**
		 * 例如 第一页   每页显示   limit第一个参数
		 *     1     12      0(0-11数据库从0开始数12条)  通过规律看出等于（当前页-1）*每页显示条数
		 *     2     12      12(12-23)
		 *     3     12      24(24-35)
		 */
		int currentStart=(currentPage-1)*currentCount;
		//查询数据库中当前页显示的商品的信息
		List<Product> currentProductList=productMapper.selectCurrentPageProductList(cid,currentStart,currentCount);
		//将查询出来的商品信息封装到分页对象中，供前台页面显示
		pageBean.setList(currentProductList);
		//将分页显示的对象返回
		return pageBean;
	}
	
	
	//站内搜索，根据输入商品名称进行模糊查询数据库
	@Override
	public List<Product> getProductNameListByInput(String inputvalue) {
		List<Product> productNameList=productMapper.getProductNameListByInput(inputvalue);
		return productNameList;
	}
	
	
	//站内搜索，当用户点击搜索按钮，进行商品表分页模糊查询
	@Override
	public PageBean selectProductInfoListByInput(String searchContent, int currentPage, int currentCount) {

		//对前台需要的数据进行查询，并且进行封装,
		//应该分页类是使用泛型，在创建对象时候确定类型
		PageBean<Product> pageBean=new PageBean<Product>();
		//设置当前页
		pageBean.setCurrentPage(currentPage);
		//设置每页显示的条数
		pageBean.setCurrentCount(currentCount);
		//设置总记录数，需要根据商品名称模糊查询，从数据库中查询该类商品的总记录数
		int totalCount=productMapper.selectTotalCountByName(searchContent).intValue();
		pageBean.setTotalCount(totalCount);
		//设置总页数，通过每页显示多少条，还有总条数，计算出一共有多少页
		int totalPage=(totalCount+currentCount-1)/currentCount;
		pageBean.setTotalPage(totalPage);
		//计算数据库limit第一个参数值，通过当前页和每页显示条数
		/**
		 * 例如 第一页   每页显示   limit第一个参数
		 *     1     12      0(0-11数据库从0开始数12条)  通过规律看出等于（当前页-1）*每页显示条数
		 *     2     12      12(12-23)
		 *     3     12      24(24-35)
		 */
		int currentStart=(currentPage-1)*currentCount;
		//查询数据库中当前页显示的商品的信息,根据商品的名称模糊分页查询
		List<Product> currentProductList=productMapper.selectCurrentPageProductListByName(searchContent,currentStart,currentCount);
		//将查询出来的商品信息封装到分页对象中，供前台页面显示
		pageBean.setList(currentProductList);
		//将分页显示的对象返回
		return pageBean;
		
	}
	

}
