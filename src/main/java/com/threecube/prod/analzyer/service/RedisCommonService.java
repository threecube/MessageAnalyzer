/**
 * 
 */
package com.threecube.prod.analzyer.service;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.threecube.cache.jedis.ShardedJedisSentinelPo
import com.threecube.prod.analzyer.enums.RedisCommandEnum;
import com.threecube.prod.analzyer.util.PropertiesUtil;ol;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * Operator about redis
 * 
 * @author dingwenbin
 *
 */
@Component
@Slf4j
public class RedisCommonService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8227240478089626731L;
	@Setter
	protected ShardedJedisSentinelPool sentinelPool;
	
	public Object execute(String redisKey, RedisCommandEnum command, String... paramters) {
		
		if(StringUtils.isBlank(redisKey)) {
			return null;
		}
		
		ShardedJedis jedis = null;
		try {
			jedis = sentinelPool.getResource();
			switch(command) {
				case HGET: {
					
					if(paramters.length < 2) {
						return -1;
					}
					
					return jedis.hget(redisKey, paramters[0]);
				}
				case HSET: {
					
					if(paramters.length < 2) {
						return -1;
					}
					return jedis.hset(redisKey, paramters[0], paramters[1]);
				}
				case HGETALL: {
					
					return jedis.hgetAll(redisKey);
				}
				case SETEX: {
					
					return jedis.setex(redisKey, PropertiesUtil.RedisKeyExpireSeconds, paramters[0]);
				}
				default: {
					log.warn("Not support redis operation {}", command);
				}
			}
		} catch(Exception e) {
			
			log.error("Failed to execute redis comman", e);
		} finally {
			
			if(jedis != null) {
				jedis.close();
			}
		}
		
		return null;
	}
}
