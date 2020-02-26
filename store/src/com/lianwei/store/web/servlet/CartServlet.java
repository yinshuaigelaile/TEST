package com.lianwei.store.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianwei.store.domain.Cart;
import com.lianwei.store.domain.CartItem;
import com.lianwei.store.domain.Product;
import com.lianwei.store.service.ProductService;
import com.lianwei.store.service.serviceImpl.ProductServiceImpl;
import com.lianwei.store.web.base.BaseServlet;
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet{
	
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		if (null==cart) {
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		String pid = req.getParameter("pid");
		int num = Integer.parseInt(req.getParameter("quantity"));
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		cart.addCartItemToCart(cartItem);
		System.out.println(cart);
		resp.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}
	
	public String clearCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取pid，获取购物车，根据pid移除购物车购物项
		String pid = req.getParameter("pid");
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		cart.clearCartItemFromCart(pid);
		resp.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}
	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		cart.clearCart();
		resp.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}
	
}
