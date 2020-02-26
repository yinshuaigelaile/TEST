package com.lianwei.store.dao.daoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.lianwei.store.dao.UserDao;
import com.lianwei.store.domain.User;
import com.lianwei.store.utils.JDBCUtils;


public class UserDaoImpl implements UserDao {

	@Override
	public void register(User user) throws SQLException {
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		 Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		 qr.update(sql, params);
		
	}

	@Override
	public String findCode(String code) throws SQLException {
		String sql = "select state from user where user.code =?";
		QueryRunner qr = new QueryRunner();
		 Object[] params = {code};
		 Connection conn = JDBCUtils.getConnection();
		 Map<String ,Object> map = qr.query(conn, sql, new MapHandler(), params);
		return map.get("state").toString();
	}

	
	@Override
	public String replaceCode(String replaceValue,String code) throws SQLException {
		String sql = "update user set state = ? where code = ?";
		QueryRunner qr = new QueryRunner();
		Object[] params ={Integer.parseInt(replaceValue),code};
		Connection conn =JDBCUtils.getConnection();
        int row= qr.update(conn,sql,params);
        return String.valueOf(row);
	}

	/**
	 * 根据传过来的激活码去找到对应的用户
	 * sql语句   select * from user where code = ？
	 * @throws SQLException 
	 */
	@Override
	public User findUserByCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = " select * from user where code = ?";
		User user = qr.query(sql,  new BeanHandler<User>(User.class), code);
		return user;
	}

	/**
	 * sql语句  update user set uid=?,username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=?
	 * @throws SQLException 
	 */
	@Override
	public void update(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=? ";
		qr.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid());
	}

	@Override
	public User userLogin(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password = ?";
		return qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword());
	}

}
