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
 * ��ʾҳ�棬������ҳ����¼ҳ�棬ע��ҳ��
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
	 * ��ʾ��ҳ
	 * 
	 */
	@RequestMapping("/index")
	public String showIndex(HttpServletRequest resquest,Model model) {
		//�ڷ�����ҳǰӦ��Ϊ��ҳ��ʾ��Ҫ׼�������ݣ�׼����
		//�����ȡsession����
		HttpSession session = resquest.getSession();
		//1.׼����ҳ��ʾ����Ʒ�������ݣ����Ҵ洢��session���У��Ա�ÿ��jspҳ�涼�ܻ�ȡ��������
		List<Category> categoryList = categoryService.selectCategoryList();
		//����Ʒ������Ϣ���ݴ洢��session����
		session.setAttribute("categoryList", categoryList);
		//2.׼��������Ʒ����
		List<Product> newProductList=productService.selectNewProductList();
		//��ʵmodel�������springmvc�û��洢���ݣ����ջὫ�����ݴ洢��request����
		model.addAttribute("newProductList", newProductList);
		//3.׼��������Ʒ������
		List<Product> hotProductList=productService.selectHotProductList();
		model.addAttribute("hotProductList", hotProductList);
		//4.�����ݴ洢��request����ת������ҳ������ʾ
		return "index";
	}
	
	

	/**
	 * ��ʾ��¼ҳ��
	 */
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	/**
	 * ��ʾע��ҳ��
	 * 
	 */
	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	

}
