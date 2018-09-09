/**
 * 
 */
package com.threecube.prod.analzyer.topology;

import org.apache.storm.topology.TopologyBuilder;

/**
 * interface to build a topology
 * 
 * @author dingwenbin
 *
 */
public interface TopoloyBuilderInterface {

	/**
	 * build topology
	 * 
	 * @return
	 */
	public TopologyBuilder buildTopology();
}
