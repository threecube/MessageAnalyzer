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
public enum GwStatisticMapper {

	DATA_ID("data_id", "dataId", java.sql.Types.VARCHAR, "data_id字段"),
	
	SENDER("sender", "sender", java.sql.Types.VARCHAR, "sender字段"),
	
	ACCEPTER("accepter", "accepter", java.sql.Types.VARCHAR, "accepter字段"),
	
	CONTEXT("context", "context", java.sql.Types.VARCHAR, "context字段"),
	
	COUNT("count", "count", java.sql.Types.INTEGER, "count字段"),
	
	STAT_DIMENSION("stat_dimension", "statDimension", java.sql.Types.CHAR, "stat_dimension字段"),
	
	GMT_CREATE("gmt_create", "gmtCreate", java.sql.Types.TIMESTAMP, "gmt_create字段"),
	
	STAT_DURATION("stat_duration", "statDuration", java.sql.Types.INTEGER, "stat_duration字段");
	
	@Getter
	private String columnName;
	
	@Getter
	private String propertyName;
	
	@Getter
	private int columnType;
	
	@Getter
	private String desc;
	
	GwStatisticMapper(String columnName, String propertyName, int columnType, String desc) {
		this.columnName = columnName;
		this.propertyName = propertyName;
		this.columnType = columnType;
		this.desc =desc;
	}
	
	public static GwStatisticMapper getByPropName(String propertyName) {
		
		if(StringUtils.isBlank(propertyName)) {
			return null;
		}
		
		GwStatisticMapper[] mappers = GwStatisticMapper.values();
		
		for(GwStatisticMapper mapper: mappers) {
			if(StringUtils.equals(propertyName, mapper.getPropertyName())) {
				return mapper;
			}
		}
		
		return null;
	}
}
