/**
 * 
 */
package com.threecube.prod.analzyer.datasource.mapper;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * @author dingwenbin
 *
 */
public enum GwUserFilterMapper {
	
	USER_ID("user_id", "userId", java.sql.Types.VARCHAR, "user_id字段"),
	
	ACCEPTER("accepter", "accepter", java.sql.Types.VARCHAR, "accepter字段"),
	
	FILTER_FROM("filter_from", "filterFrom", java.sql.Types.INTEGER, "filter_from字段"),
	
	DATA_ID("data_id", "dataId", java.sql.Types.VARCHAR, "data_id字段"),
	
	RULE_ID("rule_id", "ruleId", java.sql.Types.BIGINT, "rule_id"),
	
	RULE_TYPE("rule_type", "ruleType", java.sql.Types.CHAR, "rule_type"),
	
	IS_VALID("is_valid", "isValid", java.sql.Types.BIT, "是否生效"),
	
	VALID_DURATION("valid_duration", "validDuration", java.sql.Types.INTEGER, "有效持续时间"),
	
	GMT_CREATE("gmt_create", "gmtCreate", java.sql.Types.TIMESTAMP, "gmt_create字段"),
	
	GMT_START_VALID("gmt_start_valid", "gmtStartValid", java.sql.Types.TIMESTAMP, "gmt_start_valid字段");
	
	@Getter
	private String columnName;
	
	@Getter
	private String propertyName;
	
	@Getter
	private int columnType;
	
	@Getter
	private String desc;
	
	GwUserFilterMapper(String columnName, String propertyName, int columnType, String desc) {
		this.columnName = columnName;
		this.propertyName = propertyName;
		this.columnType = columnType;
		this.desc =desc;
	}
	
	public static GwUserFilterMapper getByPropName(String propertyName) {
		
		if(StringUtils.isBlank(propertyName)) {
			return null;
		}
		
		GwUserFilterMapper[] mappers = GwUserFilterMapper.values();
		
		for(GwUserFilterMapper mapper: mappers) {
			if(StringUtils.equals(propertyName, mapper.getPropertyName())) {
				return mapper;
			}
		}
		
		return null;
	}
}
