package com.ping.pojo;

import java.util.Date;

/**
 * ��Ʒ����
 * @author admin
 *
 */
public class Product {

	/*CREATE TABLE `product` (
	  `pid` int(11) NOT NULL AUTO_INCREMENT,
	  `pname` varchar(50) DEFAULT NULL,
	  `market_price` double DEFAULT NULL,
	  `shop_price` double DEFAULT NULL,
	  `pimage` varchar(200) DEFAULT NULL,
	  `pdate` date DEFAULT NULL,
	  `is_hot` int(11) DEFAULT NULL,
	  `pdesc` varchar(255) DEFAULT NULL,
	  `pcolor` varchar(255) DEFAULT NULL,
	  `pflag` int(11) DEFAULT NULL,
	  `cid` int(11) DEFAULT NULL,*/
	
	private Integer pid;          //��Ʒid
	private String pname;         //��Ʒ����
	private Double market_price;  //��Ʒ�г��۸�
	private Double shop_price;    //��Ʒ���ۼ۸�
	private String pimage;        //��ƷͼƬ
	private String pdate;           //��Ʒ����
	private Integer is_hot;       //��Ʒ�Ƿ�����,1�������ţ�0��������
	private String pdesc;         //��Ʒ����
	private Integer pflag;        //��Ʒ����Ƿ����¼ܣ�0�����¼ܣ�1�����ϼ�
	private Category category;    //���������ʹ������������д洢
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Double getMarket_price() {
		return market_price;
	}
	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}
	public Double getShop_price() {
		return shop_price;
	}
	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public String getPdate() {
		return pdate;
	}
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public Integer getPflag() {
		return pflag;
	}
	public void setPflag(Integer pflag) {
		this.pflag = pflag;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
	
}
