/**
 * 
 */
package com.threecube.prod.analzyer.topology;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dingwenbin
 *
 */
@Slf4j
public class TopologySubmitor {

	/**
	 * submit topology
	 */
	public static final void main(String[] args) {
		
		ApplicationContext factory = new ClassPathXmlApplicationContext("classpath*:/spring/application-*.xml");
		
		//get topology configures 
		TopologyConfigures topologyConfigures = (TopologyConfigures) factory.getBean("topologyConfigures");
		
		//get topology builder
		FirewallTopologyBuilder firewallTopologyBuilder = (FirewallTopologyBuilder) factory.getBean("firewallTopologyBuilder");
		
		if(firewallTopologyBuilder == null) {
			
			log.error("Failed to get bean firewallTopologyBuilder from applicationContext");
			System.exit(-1);
		}
		
		try {
			TopologyBuilder topologyBuilder = firewallTopologyBuilder.buildTopology();
			
			if(topologyBuilder == null) {
				log.error("Failed to creat topology builder");
				System.exit(-1);
			}
			
			StormSubmitter.submitTopology(firewallTopologyBuilder.getTopologyName(),
					createTopologyConf(topologyConfigures),
					topologyBuilder.createTopology());
			
			log.info("Success to submit topology {}", firewallTopologyBuilder.getTopologyName());
		} catch(Exception e) {
			log.error("Failed to submit topology", e);
			System.exit(-1);
		} 
		
	}
	
	private static Config createTopologyConf(TopologyConfigures topologyConfigures) {
		
		Config topologyConfig = new Config();
		topologyConfig.setMaxSpoutPending(topologyConfigures.getMaxSpoutPending());
		topologyConfig.setNumAckers(topologyConfigures.getAckerNum());
		topologyConfig.setNumWorkers(topologyConfigures.getWorkerNum());
		
		return topologyConfig;
	}
}
