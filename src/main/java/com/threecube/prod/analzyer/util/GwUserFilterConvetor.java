/**
 * 
 */
package com.threecube.prod.analzyer.util;

import java.sql.Timestamp;
import java.util.Date;

import com.threecube.prod.analzyer.datasource.Do.GwRuleDO;
import com.threecube.prod.analzyer.datasource.Do.GwStatisticDO;
import com.threecube.prod.analzyer.datasource.Do.GwUserFilterDO;
import com.threecube.prod.analzyer.enums.FilterFromEnum;
import com.threecube.prod.analzyer.model.DetectResultModel;

/**
 * @author dingwenbin
 *
 */
public class GwUserFilterConvetor {
	
	public static DetectResultModel DO2Model(GwStatisticDO statDataDO, GwRuleDO ruleDO) {
		
		if(statDataDO == null || ruleDO == null) {
			return null;
		}
		
		DetectResultModel detectResultModel = new DetectResultModel();
		
		detectResultModel.setDataId(statDataDO.getDataId());
		detectResultModel.setRuleId(ruleDO.getRuleId());
		detectResultModel.setAnormalUserId(statDataDO.getSender());
		detectResultModel.setOperationCode(ruleDO.getOperationCode());
		detectResultModel.setRuleType(ruleDO.getRuleType());
		
		detectResultModel.setValid(false);
		if(!ruleDO.isManually()) {
			
			detectResultModel.setValid(true);
		}
		
		return detectResultModel;
	}
	
	public static GwUserFilterDO Model2DO(DetectResultModel model, GwStatisticDO statDataDO) {
		
		if(model == null || statDataDO == null) {
			return null;
		}
		
		GwUserFilterDO gwUserFilterDO = new GwUserFilterDO();
		
		gwUserFilterDO.setUserId(model.getAnormalUserId());
		gwUserFilterDO.setAccepter(statDataDO.getAccepter());
		gwUserFilterDO.setDataId(model.getDataId());
		gwUserFilterDO.setFilterFrom(FilterFromEnum.SYSTEM.getCode());
		gwUserFilterDO.setRuleId(model.getRuleId());
		gwUserFilterDO.setRuleType(model.getRuleType());
		gwUserFilterDO.setValid(false);
		
		Date date = new Date();
		gwUserFilterDO.setGmtCreate(new Timestamp(date.getTime()));
		
		if(model.isValid()) {
			gwUserFilterDO.setValid(true);
			gwUserFilterDO.setValidDuration(PropertiesUtil.RedisKeyExpireSeconds);
			gwUserFilterDO.setGmtStartValid(new Timestamp(date.getTime()));
		}
		
		return gwUserFilterDO;
	}
}
