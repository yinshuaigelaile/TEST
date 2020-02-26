package com.lianwei.store.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.lianwei.store.dao.CategoryDao;
import com.lianwei.store.domain.Category;
import com.lianwei.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCategories() throws SQLException {
		String sql = "select * from category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

}
