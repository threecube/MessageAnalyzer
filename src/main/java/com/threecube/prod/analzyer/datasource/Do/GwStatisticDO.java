/**
 * 
 */
package com.threecube.prod.analzyer.datasource.Do;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class GwStatisticDO implements Serializable{

	@Getter @Setter
	private String dataId;
	
	@Getter @Setter
	private String sender;
	
	@Getter @Setter
	private String accepter;
	
	@Getter @Setter
	private String context;
	
	@Getter @Setter
	private int count;
	
	@Getter @Setter
	private String statDimension;
	
	@Getter @Setter
	private int statDuration;
	
	@Getter @Setter
	private Timestamp gmtCreate;
}
