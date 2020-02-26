package com.lianwei.store.dao.daoImpl;


import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.lianwei.store.dao.AdminCategoryDao;
import com.lianwei.store.domain.Category;
import com.lianwei.store.utils.JDBCUtils;

public class AdminCategoryDaoImpl implements AdminCategoryDao{

	@Override
	public List<Category> findAllCats() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category(cid,cname) values(?,?)";
		qr.update(sql, category.getCid(),category.getCname());
	}

	@Override
	public Category findCatByCid(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid=?";
		return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
	}

	@Override
	public void editCategory(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname=? where cid=?";
		qr.update(sql, category.getCname(),category.getCid());
	}

	@Override
	public void delCategory(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname=? where cid=?";
		qr.update(sql, null,category.getCid());
	}

}
