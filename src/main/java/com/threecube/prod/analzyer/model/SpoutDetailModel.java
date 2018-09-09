/**
 * 
 */
package com.threecube.prod.analzyer.model;

import org.apache.storm.topology.base.BaseRichSpout;

import lombok.Getter;
import lombok.Setter;

/**
 * spout beans information
 * 
 * @author dingwenbin
 *
 */
public class SpoutDetailModel{

	/**
	 * spout id(name)
	 */
	@Getter @Setter
	private String id;
	
	/**
	 * parallelism number
	 */
	@Getter @Setter
	private int parallNum;
	
	/**
	 * component bean
	 */
	@Getter @Setter
	private BaseRichSpout component;
}
