package com.lianwei.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.lianwei.store.domain.User;

@WebFilter({ "/LoginFilter", "/jsp/cart.jsp", "/jsp/order_list.jsp", "/jsp/order_info.jsp"})
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest myRequest = (HttpServletRequest) request;
		User user = (User)myRequest.getSession().getAttribute("loginUser");
		if (null!=user) {
			//登录了可以放行
			chain.doFilter(request, response);
		}else {
			//未登录
			myRequest.setAttribute("msg", "请先登录");
			myRequest.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
