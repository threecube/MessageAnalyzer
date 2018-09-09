/**
 * 
 */
package com.threecube.prod.analzyer;

import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.threecube.prod.analzyer.topology.FirewallTopologyBuilder;

import kafka.tools.ReplayLogProducer.Config;

/**
 * storm cluster local model 
 * 
 * @author dingwenbin
 *
 */
public class StormLocalService {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext factory = new ClassPathXmlApplicationContext("classpath*:/spring/application-*.xml");
		
		FirewallTopologyBuilder firewallTopologyBuilder = (FirewallTopologyBuilder) factory.getBean("firewallTopologyBuilder");
		
		if(firewallTopologyBuilder == null) {
			System.out.println("firewallTopologyBuilder is null");
			return ;
		}
		
		TopologyBuilder topologyBuilder = firewallTopologyBuilder.buildTopology();
		
		LocalCluster cluster = new LocalCluster();
		
		cluster.submitTopology(firewallTopologyBuilder.getTopologyName(),
				new Config(),
				topologyBuilder.createTopology());
		
	}
}
