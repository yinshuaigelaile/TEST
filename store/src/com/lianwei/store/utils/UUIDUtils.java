package com.lianwei.store.utils;

import java.util.UUID;

import com.mysql.jdbc.Util;

public class UUIDUtils {
	/**
	 * 随机生成id
	 * @return
	 */
	public static String getId(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	
	public static String getUUID64(){
		return getId()+getId();
	}
	
	/**
	 * 生成随机�?
	 * @return
	 */
	public static String getCode(){
		return getId();
		
	}
	public static void main(String[] args) {
//		System.out.println(getCode());
		long start= System.currentTimeMillis();
		for(long i=0;i<4294967296L;i++) {
			System.out.println(UUID.randomUUID().toString());
		}
		long end = System.currentTimeMillis();
		System.out.println(end);
		System.out.println(end-start);
	}

}
