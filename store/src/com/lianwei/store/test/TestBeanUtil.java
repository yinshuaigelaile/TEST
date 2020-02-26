package com.lianwei.store.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import com.lianwei.store.domain.User;

public class TestBeanUtil {
	@Test
	public void testBeanutil() throws Exception {
		String[] dStrings = {"fdfd"};
		Map<String,String[]> map = new HashMap<String, String[]>();
		map.put("username",new String[]{"fdfd"});
		User user = new User();
		DateConverter dt = new DateConverter();
		dt.setPattern("yyyy-mm-dd");
		ConvertUtils.register(dt, java.util.Date.class);
		BeanUtils.populate(user, map);
		System.out.println(user);
	}
	
	@Test
	public void testMyBeanutil() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("username", new String[] {"admin"});
		map.put("password", new String[] {"123456"});
		map.put("birthday", new String[] {"2017-5-6"});
		User user = new User();
		MyBeanutil.populate(user, map);
		System.out.println(user);
	}
	
	
}
