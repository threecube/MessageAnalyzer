/**
 * 
 */
package com.threecube.prod.analzyer.datasource.Do;

import java.io.Serializable;
import java.util.Date;

import com.threecube.prod.analzyer.enums.RuleTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class GwRuleDO implements Serializable{

	/**
	 * serialize code
	 */
	private static final long serialVersionUID = -6467166342950366262L;

	/**
	 * index
	 */
	@Getter @Setter
	private int ruleId;
	
	/**
	 * windows size
	 */
	@Getter @Setter
	private int windowSize;
	
	/**
	 * threshold
	 */
	@Getter @Setter
	private int threshold;
	
	/**
	 * rule dimension, map to {@link RuleTypeEnum}
	 */
	@Getter @Setter
	private String ruleType;
	
	/**
	 * rule user
	 */
	@Getter @Setter
	private String ruleUser;
	
	/**
	 * rule level, 
	 */
	@Getter @Setter
	private int ruleLevel;
	
	/**
	 * operation code
	 */
	@Getter @Setter
	private String operationCode;
	
	/**
	 * operation code
	 */
	@Getter @Setter
	private String property;
	
	/**
	 * is valid
	 */
	@Getter @Setter
	private boolean isValid;
	
	/**
	 * if data which trigger the filter rule will be affection directly
	 */
	@Getter @Setter
	private boolean isManually;
	
	/**
	 * start time to valid the rule
	 */
	@Getter @Setter
	private Date gmtCreate;
	
}
