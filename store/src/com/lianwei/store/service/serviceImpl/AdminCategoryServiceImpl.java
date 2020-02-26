package com.lianwei.store.service.serviceImpl;

import java.util.List;

import com.lianwei.store.dao.AdminCategoryDao;
import com.lianwei.store.dao.daoImpl.AdminCategoryDaoImpl;
import com.lianwei.store.domain.Category;
import com.lianwei.store.service.AdminCategoryService;
import com.lianwei.store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class AdminCategoryServiceImpl implements AdminCategoryService {

	@Override
	public List<Category> findAllCats() throws Exception{
		AdminCategoryDao adminCategoryDao = new AdminCategoryDaoImpl();
		return adminCategoryDao.findAllCats();
	}

	@Override
	public void addCategory(Category category)  throws Exception {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDaoImpl();
		adminCategoryDao.addCategory(category);
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allcats");
		JedisUtils.closeJedis(jedis);
	}

	@Override
	public Category findCatByCid(String cid) throws Exception {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDaoImpl();
		return adminCategoryDao.findCatByCid(cid);
	}

	@Override
	public void editCategory(Category category) throws Exception {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDaoImpl();
		adminCategoryDao.editCategory(category);
	}

	@Override
	public void delCategory(Category category)  throws Exception {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDaoImpl();
		adminCategoryDao.delCategory(category);
	}

}
