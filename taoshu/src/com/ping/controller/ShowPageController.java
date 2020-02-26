package com.ping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ping.pojo.Category;
import com.ping.pojo.Product;
import com.ping.service.CategoryService;
import com.ping.service.ProductService;

/**
 * 显示页面，比如首页，登录页面，注册页面
 * @author admin
 *
 */
@Controller
public class ShowPageController {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	/**
	 * 显示首页
	 * 
	 */
	@RequestMapping("/index")
	public String showIndex(HttpServletRequest resquest,Model model) {
		//在访问首页前应该为首页显示需要准备的数据，准备好
		//请求获取session对象
		HttpSession session = resquest.getSession();
		//1.准备首页显示的商品分类数据，并且存储到session域中，以便每个jsp页面都能获取到该数据
		List<Category> categoryList = categoryService.selectCategoryList();
		//将商品分类信息数据存储到session域中
		session.setAttribute("categoryList", categoryList);
		//2.准备最新商品数据
		List<Product> newProductList=productService.selectNewProductList();
		//其实model对象就是springmvc用户存储数据，最终会将该数据存储到request域中
		model.addAttribute("newProductList", newProductList);
		//3.准备热门商品的数据
		List<Product> hotProductList=productService.selectHotProductList();
		model.addAttribute("hotProductList", hotProductList);
		//4.将数据存储到request域中转发到首页进行显示
		return "index";
	}
	
	

	/**
	 * 显示登录页面
	 */
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	/**
	 * 显示注册页面
	 * 
	 */
	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	

}
