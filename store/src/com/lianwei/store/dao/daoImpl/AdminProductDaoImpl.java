package com.lianwei.store.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lianwei.store.dao.AdminProductDao;
import com.lianwei.store.domain.Product;
import com.lianwei.store.utils.JDBCUtils;

public class AdminProductDaoImpl implements AdminProductDao{

	@Override
	public int getTotalCount() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from product";
		Long totalRecodes = (Long) qr.query(sql, new ScalarHandler());
		return totalRecodes.intValue();
	}

	@Override
	public List<Product> findAllProducts(int startIndex, int pageSize) throws Exception {
		String sql = "select * from product order by pdate limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class),startIndex,pageSize);
	}
	
	@Override
	public int getTotalPushDownCount() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from product where pflag=?";
		Long totalRecodes = (Long) qr.query(sql, new ScalarHandler(),1);
		return totalRecodes.intValue();
	}

	@Override
	public void addProduct(Product product) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		qr.update(sql, params);
	}

}
