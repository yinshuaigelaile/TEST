package com.lianwei.store.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.lianwei.store.dao.AdminUserDao;
import com.lianwei.store.domain.User;
import com.lianwei.store.utils.JDBCUtils;

public class AdminUserDaoImpl implements AdminUserDao {

	@Override
	public List<User> findAllUser(String grade) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE grade=?";
		return qr.query(sql, grade, new BeanListHandler<User>(User.class));
	}

	@Override
	public List<User> findAllUser() throws Exception{
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM user";
		return qr.query(sql, new BeanListHandler<User>(User.class));
	}

}
