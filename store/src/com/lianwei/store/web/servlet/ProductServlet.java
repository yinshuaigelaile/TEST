package com.lianwei.store.web.servlet;




import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.PageModel1;
import com.lianwei.store.domain.Product;
import com.lianwei.store.service.ProductService;
import com.lianwei.store.service.serviceImpl.ProductServiceImpl;
import com.lianwei.store.web.base.BaseServlet;

import net.sf.json.JSONObject;


@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		return "/jsp/product_info.jsp";
	}	
	
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//分页，传入 
		String cid = request.getParameter("cid");
		int num = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
//		PageModel page = productService.findProductsByCidWithPage(cid,num);
		PageModel1 page = productService.findProductsByCidWithPage(cid,num);
		request.setAttribute("page", page);
		System.out.println(page);
		return "/jsp/product_list.jsp";
	}
	
	
}
