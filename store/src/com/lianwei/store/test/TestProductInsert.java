package com.lianwei.store.test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lianwei.store.dao.ProductDao;
import com.lianwei.store.dao.daoImpl.ProductDaoImpl;
import com.lianwei.store.domain.Product;
import com.lianwei.store.utils.MyBeanUtils;
import com.lianwei.store.utils.UUIDUtils;

public class TestProductInsert {
	@Test
	public void insertProduct() throws Exception {
		long start = System.currentTimeMillis();
		ProductDao productDao = new ProductDaoImpl();
		Product product = new Product();
		Map<String, String[]>  map=new HashMap<String, String[]>();
		map.put("pname",new String[]{"殷少鹏最心爱的手机"});
		map.put("market_price",new String[]{"1399"});
		map.put("shop_price",new String[]{"1299"});
		map.put("pimage",new String[]{"products/1/c_0007.jpg"});
		map.put("pdate",new String[]{"2015-11-02"});
		map.put("is_hot",new String[]{"1"});
		map.put("pdesc",new String[]{"殷少鹏是世界上最厉害的人"});
		map.put("pflag",new String[]{"0"});
		map.put("cid",new String[]{"7"});
		for(int i = 0;i<10;i++) {
			map.put("pid",new String[]{UUIDUtils.getId()});
			MyBeanUtils.populate(product, map);
			productDao.insert(product);
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	@Test
	public void insertProduct1() throws Exception {
		long start = System.currentTimeMillis();
		ProductDao productDao = new ProductDaoImpl();
		Product product = new Product();
		product.setPname("适用小米note m4小米4c小米3手机屏幕总成寄修维修单独换外屏触摸");
		product.setMarket_price(1399);
		product.setShop_price(1299);
		product.setPimage("products/1/c_0005.jpg");
		product.setPdate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-09-02"));
		product.setIs_hot(1);
		product.setPdesc("小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待");
		product.setPflag(0);
		product.setCid("7");
		for(int i = 0;i<10000;i++) {
			product.setPid(UUIDUtils.getId());
			productDao.insert(product);
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
