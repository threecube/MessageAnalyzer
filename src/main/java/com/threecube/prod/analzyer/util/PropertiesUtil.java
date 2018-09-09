/**
 * 
 */
package com.threecube.prod.analzyer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dingwenbin
 *
 */
@Slf4j
public class PropertiesUtil {
	
	/**
	 * maximum size of blocking queue for saving statistic data
	 */
	public static int MaxStatQueueSize = 10000;
	
	/**
	 * maximum size of blocking queue for saving messages
	 */
	public static int MaxMessageQueueSize = 1000;
	
	/**
	 * redis key expire time(s)
	 */
	public static int RedisKeyExpireSeconds = 300;
	
	/**
	 * tuple fields name
	 */
	public static String[] TupleFields = null;
	
	/**
	 * maximum message number broadcastered to client once
	 */
	public static int EsperWindowSize = 60;
	
	static {
		Properties properties = new Properties();
		InputStream inputStream = null;
		
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties");
			properties.load(inputStream);
			
			if(properties.containsKey("stat.data.queue.size")) {
				MaxStatQueueSize = Integer.parseInt(properties.getProperty("stat.data.queue.size"));
			}
			
			if(properties.containsKey("message.queue.size")) {
				MaxMessageQueueSize = Integer.parseInt(properties.getProperty("message.queue.size"));
			}
			
			if(properties.containsKey("redis.key.expire.seconds")) {
				RedisKeyExpireSeconds = Integer.parseInt(properties.getProperty("redis.key.expire.seconds"));
			}
			
			if(properties.containsKey("stat.windows.size")) {
				EsperWindowSize = Integer.parseInt(properties.getProperty("stat.windows.size"));
			}
			
			if(properties.containsKey("tuple.fields.name")) {
				
				String[] fields = StringUtils.split(properties.getProperty("tuple.fields.name"), ",");
				
				if(fields != null) {
					
					TupleFields = new String[fields.length];
					int i = 0;
					for(String field : fields) {
						
						TupleFields[i++] = field.trim();
					}
				}
			}
			
		} catch (IOException e) {
			
			log.error("Failed to read properties", e);
			System.exit(-1);
		} finally {
			
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("Failed to close inputStream", e);
				}
			}
			
		}
	}
}
