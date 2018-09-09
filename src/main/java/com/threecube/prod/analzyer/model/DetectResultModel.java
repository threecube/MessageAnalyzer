/**
 * 
 */
package com.threecube.prod.analzyer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class DetectResultModel {

	@Getter @Setter
	private String anormalUserId;
	
	/**
	 * stat data id, value is from dataId of {@link GwFlowStatDO}
	 */
	@Getter @Setter
	private String dataId;
	
	/**
	 * rule id, value is from ruleId of {@link GwWordsRuleDO} or {@link GwFlowRuleDO}
	 */
	@Getter @Setter
	private int ruleId;
	
	/**
	 * operation code
	 */
	@Getter @Setter
	private String operationCode;
	
	/**
	 * if the this filter is in valid
	 */
	@Getter @Setter	
	private boolean isValid;
	
	@Getter @Setter
	private String ruleType;
}
