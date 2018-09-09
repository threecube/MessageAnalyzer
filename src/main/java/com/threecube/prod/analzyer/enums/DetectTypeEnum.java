/**
 * 
 */
package com.threecube.prod.analzyer.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * detect type
 * 
 * @author dingwenbin
 *
 */
public enum DetectTypeEnum {

	FLOW_DETECT("1", "detector based on message flow"),
	
	CONTENT_DETECT("2", "detector based on message content");
	
	@Getter @Setter
	private String code;
	
	@Getter @Setter
	private String desc;
	
	DetectTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
}
