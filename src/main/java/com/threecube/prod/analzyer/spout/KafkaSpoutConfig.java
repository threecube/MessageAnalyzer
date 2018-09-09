/**
 * 
 */
package com.threecube.prod.analzyer.spout;

import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;

import com.threecube.prod.analzyer.model.ChatMessageScheme;

/**
 * kafka spout configure
 * 
 * @author dingwenbin
 *
 */
public class KafkaSpoutConfig extends SpoutConfig{
	
	/**
	 * serialize code
	 */
	private static final long serialVersionUID = 8550318494917470088L;
	
	public KafkaSpoutConfig(BrokerHosts hosts, String topic, String zkRoot, String id) {
		super(hosts, topic, zkRoot, id);
		
		this.id = "message-firewall-consumer";
		this.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
		this.scheme = new SchemeAsMultiScheme(new ChatMessageScheme());
		
		/* 
		 * ignore offset submitted into zookeeper when re-deploy topology, since
		 * it is no-necessary to consumer old messages
		 */
		this.ignoreZkOffsets = true;
	}
}
