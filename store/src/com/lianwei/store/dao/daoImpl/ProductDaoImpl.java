package com.lianwei.store.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lianwei.store.dao.ProductDao;
import com.lianwei.store.domain.Product;
import com.lianwei.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao{
	
	@Override
	public List<Product> getHotProducts() throws Exception {
		String sql = "select * from product where pflag = 0 and is_hot = 1 order by pdate desc limit 0 , 9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> getNewProducts() throws Exception {
		String sql = "select * from product where pflag = 0 order by pdate desc limit 0 , 9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findProductByPid(String pid) throws Exception{
		String sql  = "select * from product where pid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	/**
	 * 根据cid查询对应的记录数
	 * @throws Exception 
	 */
	@Override
	public int getTotalRecordsByCid(String cid) throws Exception {
		String sql = "select count(*) from product where cid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long  totalRecords = (Long) qr.query(sql, new ScalarHandler(), cid);
		return totalRecords.intValue();
	}

	@Override
	public List<Product> findProductsByCidWithPage(String cid,int startIndex, int pageSize) throws Exception {
		String sql = " select * from product where cid = ? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid,startIndex,pageSize);
	}
	
	
	@Override
	public void insert(Product product) throws Exception {
		String sql = "INSERT INTO product VALUES (?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		qr.update(sql, params);
	}
	
}
