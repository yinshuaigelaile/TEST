package com.ping.pojo;

import java.io.Serializable;

/**
 * ��Ʒ������Ϣ
 * @author admin
 *
 */
public class Category implements Serializable {

	/*CREATE TABLE `category` (
	 `cid` int(11) NOT NULL AUTO_INCREMENT,
	 `cname` varchar(20) DEFAULT NULL,
	 PRIMARY KEY (`cid`)
	) */
	private Integer cid;   //��Ʒ����id
	private String cname;  //��Ʒ��������
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
