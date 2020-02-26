package com.lianwei.store.test;


import com.lianwei.store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class ClearRedis {
	
	public static void main(String[] args) {
			Jedis jedis = JedisUtils.getJedis();
			jedis.del("allcats");
			JedisUtils.closeJedis(jedis);
	}
}
