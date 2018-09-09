/**
 * 
 */
package com.threecube.prod.analzyer.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Rule for user, which is mapped with column {@code rule_user} of table {@code gw_flow_rule}
 * 
 * @author dingwenbin
 *
 */
public enum RuleUserEnum {
	
	ALL("0", "面向所有的用户的规则"),
	
	BUYER("1", "面向买家的规则"),
	
	SELLER("2", "面向卖家的规则"),
	
	BUYER_SELLER("3", "面向买家和卖家的规则"),
	
	WAITER("4", "面向客户端的规则");
	
	@Getter @Setter
	private String code;
	
	@Getter @Setter
	private String desc;
	
	RuleUserEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static RuleUserEnum getByCode(String code) {
		
		if(StringUtils.isBlank(code)) {
			
			return null;
		}
		
		RuleUserEnum[] enums = RuleUserEnum.values();
		
		for(RuleUserEnum item : enums) {
			
			if(StringUtils.equals(item.getCode(), code)) {
				return item;
			}
		}
		
		return null;
	}
}
