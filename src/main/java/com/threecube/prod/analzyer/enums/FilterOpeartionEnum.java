/**
 * 
 */
package com.threecube.prod.analzyer.enums;

import lombok.Getter;

/**
 * @author dingwenbin
 *
 */
public enum FilterOpeartionEnum {

	WARN_TO_SENDER(1, "告警发送方"),
	
	WARN_TO_ACCEPTER(2, "预警接收方"),
	
	BAN_SPEAK(3, "禁言");
	
	@Getter
	private int code;
	
	@Getter
	private String desc;
	
	private FilterOpeartionEnum(int code, String desc) {
		
		this.code = code;
		this.desc = desc;
	}
}
