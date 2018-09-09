/**
 * 
 */
package com.threecube.prod.analzyer.model;

import java.util.Map;

import org.apache.storm.topology.IRichBolt;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class BoltDetailModel {

	/**
	 * bolt id(name)
	 */
	@Getter @Setter
	private String id;
	
	/**
	 * parallelism number
	 */
	@Getter @Setter
	private int parallNum;
	
	/**
	 * map from componentId to streamId of upstream bolts
	 * 
	 * <p>if streamId are not used, set value "" or null
	 */
	@Getter @Setter
	private Map<String, String> upstreamIdMap;
	
	/**
	 * bolt component
	 */
	@Getter @Setter
	private IRichBolt component;
}
