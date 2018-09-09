/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import com.threecube.prod.analzyer.enums.RedisCommandEnum;
import com.threecube.prod.analzyer.service.RedisCommonService;
import com.threecube.prod.analzyer.util.RedisKeyUtils;
import com.threecube.cache.jedis.ShardedJedisSentinelPool;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * bolt for redis to insert tuple into redis
 * 
 * <p> this bolt's tuples are from AnomalFlowDetectBolt's stream redis-common-stream
 * <p> more details about this bolt can be found from {@code redisCommonBoltDetail}
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class RedisCommonBolt extends AbstractIRichBolt{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2574149464677722386L;

	private ShardedJedisSentinelPool sentinelPool;
	
	private RedisCommonService redisCommonService;
	
	/**
	 * redis servers
	 */
	@Setter
	private String masterNames;
	
	/**
	 * sentinels
	 */
	@Setter
	private String sentinels;
	
	/**
	 * redis configures 
	 */
	@Setter
	private Map<String, String> redisConfigs;
	
	@Setter
	private int timeout;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		super.prepare(stormConf, context, collector);
		
		//Since GenericObjectPoolConfig cann't be serialized, create it in bolt
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(Integer.valueOf(redisConfigs.get("minIdle")));
		poolConfig.setMaxIdle(Integer.valueOf(redisConfigs.get("maxIdle")));
		poolConfig.setMaxTotal(Integer.valueOf(redisConfigs.get("maxTotal")));
		poolConfig.setMaxWaitMillis(Long.valueOf(redisConfigs.get("maxWaitMillis")));
		poolConfig.setTestWhileIdle(Boolean.valueOf(redisConfigs.get("testWhileIdle")));
		poolConfig.setMinEvictableIdleTimeMillis(Long.valueOf(redisConfigs.get("minEvictableIdleTimeMillis")));
		poolConfig.setSoftMinEvictableIdleTimeMillis(Long.valueOf(redisConfigs.get("softMinEvictableIdleTimeMillis")));
		poolConfig.setTimeBetweenEvictionRunsMillis(Long.valueOf(redisConfigs.get("timeBetweenEvictionRunsMillis")));
		
		// since ShardedJedisSentinelPool cann't be serialized, create it in bolt.
		sentinelPool = new ShardedJedisSentinelPool(masterNames, sentinels, poolConfig, timeout);
		
		redisCommonService = new RedisCommonService();
		redisCommonService.setSentinelPool(sentinelPool);
	};
	
	@Override
	public void execute(Tuple input) {
		String sender = input.getStringByField("sender");
		String accepter = input.getStringByField("accepter");
		String operation = input.getStringByField("operation");
		String ruleType = input.getStringByField("ruletype");
		
		if(StringUtils.isNotBlank(sender)) {
			
			String key = RedisKeyUtils.genFilterUserKey(sender, accepter);
			String value = operation + "+" + ruleType;
			log.info("Be valid immediately, and write into redis, key:{}, value:{}", key, value);
			redisCommonService.execute(key, RedisCommandEnum.SETEX, value);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

}
