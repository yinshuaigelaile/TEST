package com.lianwei.store.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class MyBeanutil {

	public static void populate(Object obj,Map<String, String[]> map) throws Exception {
		DateConverter dt = new DateConverter();
		dt.setPattern("yyyy-mm-dd");
		ConvertUtils.register(dt, java.util.Date.class);
		BeanUtils.populate(obj, map);
	}
}
