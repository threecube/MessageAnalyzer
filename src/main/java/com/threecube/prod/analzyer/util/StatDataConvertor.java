/**
 * 
 */
package com.threecube.prod.analzyer.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.threecube.prod.analzyer.datasource.Do.GwStatisticDO;

/**
 * @author dingwenbin
 *
 */
public class StatDataConvertor {
	
	/**
	 * generate FlowStatData model from given statistic informations
	 * 
	 * @param dataId
	 * @param sender
	 * @param accepter
	 * @param dimension
	 * @param count
	 * @return
	 */
	public static GwStatisticDO genModelA(String sender, String accepter, String dimension, int count) {
		
		if(StringUtils.isBlank(dimension)) {
			return null;
		}
		
		GwStatisticDO flowStatDataDO = new GwStatisticDO();
		flowStatDataDO.setSender(sender);
		flowStatDataDO.setAccepter(accepter);
		
		flowStatDataDO.setDataId(UUID.randomUUID().toString());
		
		flowStatDataDO.setGmtCreate(new Timestamp(new Date().getTime()));
		flowStatDataDO.setStatDuration(PropertiesUtil.EsperWindowSize);
		flowStatDataDO.setStatDimension(dimension);
		flowStatDataDO.setCount(count);
		
		return flowStatDataDO;
	}
	
	/**
	 * generate FlowStatData model from given statistic informations
	 * 
	 * @param dataId
	 * @param sender
	 * @param accepter
	 * @param dimension
	 * @param count
	 * @return
	 */
	public static GwStatisticDO genModelB(String sender, String content, String dimension, int count) {
		
		if(StringUtils.isBlank(dimension)) {
			return null;
		}
		
		GwStatisticDO textStatDataDO = new GwStatisticDO();
		textStatDataDO.setSender(sender);
		textStatDataDO.setContext(content);
		
		textStatDataDO.setDataId(UUID.randomUUID().toString());
		
		textStatDataDO.setGmtCreate(new Timestamp(new Date().getTime()));
		textStatDataDO.setStatDuration(PropertiesUtil.EsperWindowSize);
		textStatDataDO.setStatDimension(dimension);
		textStatDataDO.setCount(count);
		
		return textStatDataDO;
	}
}
