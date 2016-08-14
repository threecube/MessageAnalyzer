/**
 * 
 */
package com.threecube.messageAnalyzer.topology;

import com.threecube.messageAnalyzer.bolt.ReceiverCountStatBolt;
import com.threecube.messageAnalyzer.bolt.UserMsgNumMapStatBolt;
import com.threecube.messageAnalyzer.bolt.UserMsgNumStatBolt;
import com.threecube.messageAnalyzer.spout.ChatMessageSpout;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * @author wenbin_dwb
 *
 */
public class MessageAnalyzerTopology {
	private Config config;
	
	public void create(){
		
		try{
			TopologyBuilder builerBuilder = createBuilder();
			StormSubmitter.submitTopology("chat-message-stat", createStormConfig(), builerBuilder.createTopology());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * define storm topology builder
	 * */
	private TopologyBuilder createBuilder() {
		TopologyBuilder builder = new TopologyBuilder();
		
		//spout
		builder.setSpout("kafka-message", new ChatMessageSpout(), 1);

		//bolt for total messages number statistic sent by user to receiver
		//builder.setBolt("user-msg-receiver-num-stat", new UserMsgNumMapStatBolt(), 2)
		//	.fieldsGrouping("kafka-message", new Fields("userFrom", "userTo"));
		
		
		//bolt for total messages number statistic sent by user
		//builder.setBolt("user-msg-num-stat", new UserMsgNumStatBolt(), 2).
		//	fieldsGrouping("user-msg-receiver-num-stat", new Fields("userFrom"));
		
		
		//bolt for total receiver count connected by user
		builder.setBolt("receiver-num-sent-by-user", new ReceiverCountStatBolt(), 2)
			.fieldsGrouping("kafka-message", new Fields("userFrom", "userTo"));
		
		return builder;
	}
	
	private Config createStormConfig() {
		Config config = new Config();
		config.setNumWorkers(2);
		return config;
	}
	
}
