/**
 * 
 */
package com.threecube.messageAnalyzer.spout;

import java.util.Map;

import com.threecube.messageAnalyzer.kafka.KafkaSimpleReader;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.commons.lang.math.RandomUtils;

/**
 * @author wenbin_dwb
 *
 */
public class ChatMessageSpout extends AbstractIRichSpout {

	private transient SpoutOutputCollector collector;

	public void nextTuple() {
//		// TODO Auto-generated method stub
//		String chatMessage = KafkaSimpleReader.blockingMessQueue.poll();
//		if(StringUtils.isEmpty(chatMessage)){
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else{
//			ChatMessageModel model = ChatMessageParseUtils.parse(chatMessage);
//			String userFrom = model.getUserFrom();
//			String userTo = model.getUserTo();
//			String message = model.getMessage();
//			if(!StringUtils.isEmpty(userFrom) && !StringUtils.isEmpty(userTo)){
//				this.collector.emit(new Values(userFrom, userTo, message));
//			}
//		}

		this.collector.emit(new Values(RandomUtils.nextInt(100), RandomUtils.nextInt(50), String.valueOf(System.currentTimeMillis())));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//定义spout发出去的tuple字段结构
		declarer.declare(new Fields("userFrom", "userTo", "message"));
	}


	public void open(Map stormConfig, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		
		
	}

	public void close() {
		// TODO Auto-generated method stub
		KafkaSimpleReader.stopRead();
	}
}
