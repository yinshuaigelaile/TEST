package com.lianwei.store.web.servlet;


import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianwei.store.domain.Category;
import com.lianwei.store.service.CategoryService;
import com.lianwei.store.service.serviceImpl.CategoryServiceImpl;
import com.lianwei.store.utils.JedisUtils;
import com.lianwei.store.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	public String findAllCategries(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		//在redis中获取全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allcats");
		if(null==jsonStr||"".equals(jsonStr)) {
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> categories = categoryService.getAllCategories();
			jsonStr = JSONArray.fromObject(categories).toString();
			System.out.println(jsonStr);
			jedis.set("allcats", jsonStr);
			System.out.println("redis缓存中没有数据");
			
		}else {
			System.out.println("redis缓存中有数据");
		}
		//将全部分类信息响应到客户端
		//告诉浏览器本次响应地数据时JSON格式地字符串
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);	
		JedisUtils.closeJedis(jedis); 
		return null;
	}
}
