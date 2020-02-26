package com.lianwei.store.test;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.lianwei.store.dao.UserDao;
import com.lianwei.store.dao.daoImpl.UserDaoImpl;
import com.lianwei.store.web.servlet.UserServlet;

public class TestFindCode {
	@Test
	public void testFindCode() {
		UserDao userDao = new UserDaoImpl();
		try {
			String string = userDao.findCode("6AF804D37B1B49B2BB8EFA8D30C4AD2C");
			System.out.println(string);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestReplaceCode() throws SQLException {
		UserDao userDao = new UserDaoImpl();
		String replaceValue = "1";
		String codeValue = "0";
		String code = "6AF804D37B1B49B2BB8EFA8D30C4AD2C";
		if("0".equals(codeValue)) {
			userDao.replaceCode(replaceValue,code);
		}
	}
	

	
}
