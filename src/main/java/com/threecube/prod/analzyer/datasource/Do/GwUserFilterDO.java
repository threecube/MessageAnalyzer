/**
 * 
 */
package com.threecube.prod.analzyer.datasource.Do;

import java.io.Serializable;
import java.sql.Timestamp;

import com.threecube.prod.analzyer.enums.FilterFromEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class GwUserFilterDO implements Serializable{
	
//	@Getter @Setter
//	private long filterId;
	
	@Getter @Setter
	private String userId;
	
	@Getter @Setter
	private String accepter;
	
	/**
	 * where filter is from, value is from code of {@link FilterFromEnum}
	 */
	@Getter @Setter
	private int filterFrom;
	
	/**
	 * stat data id, value is from dataId of {@link GwFlowStatDO}
	 */
	@Getter @Setter
	private String dataId;
	
	/**
	 * rule id, value is from ruleId of {@link GwWordsRuleDO} or {@link GwFlowRuleDO}
	 */
	@Getter @Setter
	private long ruleId;
	
	/**
	 * rule type
	 */
	@Getter @Setter
	private String ruleType;
	
	/**
	 * if the this filter is in valid
	 */
	@Getter @Setter	
	private boolean isValid;
	
	/**
	 * duration of valid (s)
	 */
	@Getter @Setter
	private int validDuration;
	
	/**
	 * create time
	 */
	@Getter @Setter
	private Timestamp gmtCreate;
	
	/**
	 * the time of starting to be valid
	 */
	@Getter @Setter
	private Timestamp gmtStartValid;
}
