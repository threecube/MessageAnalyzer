/**
 * 
 */
package com.threecube.messageAnalyzer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wenbin_dwb
 *
 */
public class PropertiesUtils {
	
	private static Properties properties = new Properties();

	static {
		InputStream inputStream = null  ;
		try {
			inputStream = Object.class.getResourceAsStream("/kafka.properties");
			properties.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getKafkaBrokerList() {
		return properties.contains("kafka.broker.list") ? 
				properties.getProperty("kafka.broker.list") : "127.0.0.1";
	}
	
	public static int getKafkaPort () {
		return properties.contains("kafka.port") ? 
				Integer.valueOf(properties.getProperty("kafka.port")) : 9092;
	}
	
	public static int getSoTimeOut() {
		return properties.contains("kafka.socket.timeout") ?
				Integer.valueOf(properties.getProperty("kafka.socket.timeout")) : 2000;
	}
	
	public static int getKafkaBufferSize () {
		return properties.contains("kafka.buffer.size") ?
				Integer.valueOf(properties.getProperty("kafka.buffer.size")) : 1024*1024;
	}
	
	public static String getConsumerName () {
		return properties.contains("kafka.consumer.name") ?
				properties.getProperty("kafka.consumer.name") : "consumer_test";
	}
}
