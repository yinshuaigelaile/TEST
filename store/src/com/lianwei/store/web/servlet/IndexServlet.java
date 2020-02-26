package com.lianwei.store.web.servlet;


import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.lianwei.store.domain.Product;

import com.lianwei.store.service.ProductService;

import com.lianwei.store.service.serviceImpl.ProductServiceImpl;
import com.lianwei.store.web.base.BaseServlet;
@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//调用业务层功能，获取全部分类信息，	返回集合
//		CategoryService categoryService = new CategoryServiceImpl();
//		List<Category> allCategoryList=categoryService.getAllCategories();
//		//将集合放入request
//		request.setAttribute("allCategoryList", allCategoryList);
		//获取到最新的和最热的商品list形式
		ProductService productService = new ProductServiceImpl();
		List<Product> list01 = productService.getHotProducts();
		List<Product> list02 = productService.getNewProducts();
		request.setAttribute("Hots", list01);
		request.setAttribute("News", list02);
		//转发到真实的首页
		return "/jsp/index.jsp";
	}
}
