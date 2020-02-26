package com.lianwei.store.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;



import net.sf.json.JSONArray;

public class TestJson {
	
//	@Test
//	public void testJson() throws Exception {
//		CategoryService categoryService = new CategoryServiceImpl();
//		List<Category> categories = categoryService.getAllCategories();
//		System.out.println(categories);
//		String jsonStr = JSONArray.fromObject(categories).toString();
//		System.out.println(jsonStr);
//	}
	
	@Test
	public void testJson() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		map.put("cid", "1");
		map.put("cname", "图书");
		map1.put("cid", "2");
		map1.put("cname", "电器");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list.add(map);
		list.add(map1);
		System.out.println(list); 
		String jsonStr = JSONArray.fromObject(list).toString();
		System.out.println(jsonStr);
	}
}
