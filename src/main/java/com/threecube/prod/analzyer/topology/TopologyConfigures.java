/**
 * 
 */
package com.threecube.prod.analzyer.topology;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class TopologyConfigures {

	@Getter @Setter
	private String topologyName;
	
	@Getter @Setter
	private int workerNum;
	
	@Getter @Setter
	private int ackerNum;
	
	@Getter @Setter
	private int maxSpoutPending;
}
