/**
 * 
 */
package com.threecube.messageAnalyzer.kafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kafka.javaapi.consumer.SimpleConsumer;

/**
 * kafka simple client which storing messages from KafkaSimpleReader
 * 
 * @author wenbin_dwb
 *
 */
public class KafkaSimpleReader{
	
    /**
     * storing message from kafka
     * */
	public volatile static BlockingQueue<String> blockingMessQueue;
	
	private SimpleConsumer consumer; 
	
	private transient static ExecutorService executorService;
	
	private void init(){
		
		//using thread pool to read messages from kafka
		executorService = Executors.newFixedThreadPool(2);
		
	}

	/**
	 * 
	 * */
	public static void startRead() {
//		executorService.submit(new Runnable() {
//			public void run() {
//				blockingMessQueue.add("@@@@");
//			}
//		});
		
	}

	public static void stopRead() {
		if(executorService != null){
			executorService.shutdown();
		}
	}
}
