package com.ping.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Product;
import com.ping.service.ProductService;

/**
 * 商品有关的controller层
 * @author admin
 *
 */
@Controller
public class ProductController {

	//通过注解方式自动按照类型将service层对象注入到该成员属性
	@Autowired
	private ProductService productService;
	
	//根据商品的pid查询单个商品的信息
	@RequestMapping("/productInfo")
	public String selectProductInfo(@RequestParam int pid,Model model)
	{

		//根据商品id查询商品信息
		Product productInfo = productService.selectProductInfoById(pid);
		//将数据存储到model中，自动会存储到request域
		model.addAttribute("productInfo", productInfo);
		//转发到product_info.jsp页面进行显示
		return "product_info";
	}
	
	
	//显示商品的列表信息并且分页显示
	@RequestMapping("/productListInfo")
	public String selectProductListInfo(@RequestParam int cid,@RequestParam(defaultValue="1") int currentPage,Model model)
	{
		//根据商品类别查询商品信息并且分页显示
		//int currentPage=1;//当前页
		int currentCount=12;//每页显示的条数
		//调用service层，进行对前台需要的数据进行查询和封装
		PageBean pageBean=productService.selectProductListByCid(cid,currentPage,currentCount);
		//将分页需要显示的数据存储到model，自动存到request域中
		model.addAttribute("pageBean", pageBean);
		//将商品分类cid存储到request域中
		model.addAttribute("cid", cid);
        //转发到商品列表信息显示
		return "product_list";
	}
	
	/**
	 * 站内搜索
	 */
	
    //根据前台ajax传输过来的文本框输入值作为商品名称，进行模糊查询数据库，获取相应商品的名称返回
	@RequestMapping("/getProductNameByInput")
	@ResponseBody   //通过该注解会自动将返回的对象、其他东西转换成json格式的字符串
	public List<Product> getProductNameListByInput(String inputvalue)
	{
		//通过输入的值作为商品的名称进行模糊查询数据库,将数据传输到service进行查询
		List<Product> productNameList=productService.getProductNameListByInput(inputvalue);
		//返回一个集合对象，通过注解自动转换成json格式字符串，返回到前台
		return productNameList;
	}
	
	//当用户点击搜索按钮，获取用户输入的信息，进行数据库商品表名称的模糊分页查询，返回list集合数据
	@RequestMapping("/selectProductInfoListByInput")
	public String selectProductInfoListByInput(String searchContent,@RequestParam(defaultValue="1") int currentPage,Model model )
	{
		
		//解决一些get提交乱码的问题  UTF-8编码->UTF-8(iso-8859-1)编码->iso-8859-1解码->UTF-8解码
		String searchContent1=null;
		try {
			searchContent1 = new String(searchContent.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int currentCount=12;//每页显示的条数
		//获取前台提交的搜索框内容，调用service层方法进行模糊分页查询,返回封装好的分页对象
		PageBean pageBeanProduct=productService.selectProductInfoListByInput(searchContent1,currentPage,currentCount);
		//将分页对象存储到model里面会自动存储到request域中，springmvc框架优点
		model.addAttribute("pageBeanProduct",pageBeanProduct );
		//同时还要存储搜索商品的信息，返回页面到时候分页点击某一个页面，需要带上这个搜索信息进行模糊查询
		model.addAttribute("searchContent", searchContent1);
		//转发到查询商品列表页面进行显示
		return "search_productList";
	}
	
}
