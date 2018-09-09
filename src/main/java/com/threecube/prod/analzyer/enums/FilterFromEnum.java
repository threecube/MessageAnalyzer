/**
 * 
 */
package com.threecube.prod.analzyer.enums;

import lombok.Getter;

/**
 * @author dingwenbin
 *
 */
public enum FilterFromEnum {

	USER(1, "用户设置"),
	
	SYSTEM(2, "系统生成");
	
	@Getter
	private int code;
	
	@Getter
	private String desc;
	
	private FilterFromEnum(int code, String desc) {
		
		this.code = code;
		this.desc = desc;
	}
}
