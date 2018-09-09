/**
 * 
 */
package com.threecube.prod.analzyer.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * @author dingwenbin
 *
 */
public enum RuleTypeEnum {
	
	ONE_2_ONE_MSG_NUM("1", "rule for checking messages volume based on one to one"),
	
	ONE_2_MANY_MSG_NUM("2", "rule for checking messages volume based on one to many"),
	
	MULTI_2_MULTI_MSG_NUM("3", "rule for checking messages volume based on many to many"),
	
	CONN_RECEIVER_NUM("4", "rule for checking accepters count with one sender"),
	
	URL_MSG_NUM("5", "rule for checking messages count containing url from one sender"),
	
	KEYWORD_MSG_NUM("6", "rule for checking messages count containing keyword from one sender");
	
	@Getter
	private String code;
	
	@Getter
	private String desc;
	
	RuleTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * get by code
	 * 
	 * @param code
	 * @return
	 */
	public RuleTypeEnum getByCode(String code) {
		
		if(StringUtils.isBlank(code)) {
			
			return null;
		}
		
		for(RuleTypeEnum type : RuleTypeEnum.values()) {
			if(StringUtils.equals(type.getCode(), code)) {
				
				return type; 
			}
		}
		
		return null;
	}
}
