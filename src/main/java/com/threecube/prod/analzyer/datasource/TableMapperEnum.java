/**
 * 
 */
package com.threecube.prod.analzyer.datasource;

import com.threecube.prod.analzyer.datasource.mapper.GwStatisticMapper;
import com.threecube.prod.analzyer.datasource.mapper.GwUserFilterMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public enum TableMapperEnum {
	
	GW_STATTSTIC("gw_statistic", GwStatisticMapper.class),
	
	GW_USER_FILTER("gw_user_filter", GwUserFilterMapper.class);
	
	/**
	 * table name
	 */
	@Getter @Setter
	private String tableName;
	
	/**
	 * mapper class of this table
	 */
	@Getter @Setter
	private Class mapperClass;
	
	private TableMapperEnum(String tableName, Class mapperClass) {
		this.tableName = tableName;
		this.mapperClass = mapperClass;
	}
}
