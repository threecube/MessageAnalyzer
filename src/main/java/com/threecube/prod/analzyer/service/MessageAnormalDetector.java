/**
 * 
 */
package com.threecube.prod.analzyer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.threecube.prod.analzyer.datasource.Dao.GwRuleDao;
import com.threecube.prod.analzyer.datasource.Do.GwRuleDO;
import com.threecube.prod.analzyer.datasource.Do.GwStatisticDO;
import com.threecube.prod.analzyer.enums.RuleTypeEnum;
import com.threecube.prod.analzyer.model.DetectResultModel;
import com.threecube.prod.analzyer.util.GwUserFilterConvetor;
import com.threecube.prod.analzyer.util.RuleUserTypeUtil;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * detector based on content of messages 
 * 
 * @author dingwenbin
 *
 */
@Component
@Slf4j
public class MessageAnormalDetector implements GwDetectorInterface <GwStatisticDO> {
	
	/**
	 * serialize code
	 */
	private final long serialVersionUID = -6607850604564570036L;
	
	/**
	 * data from table gw_text_rule
	 * <p> don't modify it as a statistic variable
	 */
	@Getter
	private List<GwRuleDO> gwRuleDOs;
	
	/**
	 * keyword list from table gw_text_rule
	 * <p> don't modify it as a statistic variable
	 */
	@Getter
	private List<String> keyWordList = null;
	
	// constructor, load rules and update into cache
	public MessageAnormalDetector(GwRuleDao gwRuleDao) {
		
		/* since some class in mybatis is non-serializable, 
		 * load data from db before serialize MessageFrequentDetector */
		updateTextRule(gwRuleDao.selectValid());
	}
	
	@Override
	public DetectResultModel detect(GwStatisticDO statDO) {
		
		if(statDO == null) {
			return null;
		}
		
		for(GwRuleDO ruleDO : gwRuleDOs) {
			
			if(StringUtils.equals(ruleDO.getRuleType(), statDO.getStatDimension()) &&
					RuleUserTypeUtil.isRuleForUser(ruleDO.getRuleUser(), statDO.getSender())) {
				
				/* since filterFlowRuleDOs is ordered by filter_level in desc, 
				 * get the first rule whose threshold is smaller than datamodel
				 */
				if(ruleDO.getThreshold() <= statDO.getCount()) {
					
					if((StringUtils.equals(ruleDO.getRuleType(), RuleTypeEnum.URL_MSG_NUM.getCode()) ||
							StringUtils.equals(ruleDO.getRuleType(), RuleTypeEnum.KEYWORD_MSG_NUM.getCode()))) {
						
						if(!StringUtils.contains(statDO.getContext(), ruleDO.getProperty())) {
							return null;
						}
					} 
					
					log.info("Detect abnormal user {} by content rule (id:{}), statistic value is " + statDO.getCount(), statDO.getSender(), ruleDO.getRuleId());
					
					DetectResultModel result = GwUserFilterConvetor.DO2Model(statDO, ruleDO);
					
					return result;
				}
			}
		}
			
		return null;
	}
	
	/**
	 * find if there is keyword in message, return matched keyword
	 * 
	 * @param message
	 * @return
	 */
	public String keyWordFind(String message) {
		
		if(StringUtils.isBlank(message)) {
			return null;
		}
		
		for(String keyWord : keyWordList) {
			
			if(StringUtils.contains(message, keyWord)) {
				
				return keyWord;
			}
		}
		
		return null;
	}
	
	/**
	 * update filter rules by text
	 * 
	 * @param flowRules
	 */
	public void updateTextRule(List<GwRuleDO> rules) {
		
		if(!CollectionUtils.isEmpty(rules)) {
			
			if(gwRuleDOs == null) {
				gwRuleDOs = new ArrayList<>();
			}
			
			gwRuleDOs.clear();
			gwRuleDOs.addAll(rules);
			
			//get all key word black list
			if(keyWordList == null) {
				keyWordList = new ArrayList<>();
			} else {
				keyWordList.clear();
			}
			
			for(GwRuleDO rule : rules) {
				
				if(StringUtils.equals(rule.getRuleType(), RuleTypeEnum.KEYWORD_MSG_NUM.getCode())) {
					keyWordList.add(rule.getProperty());
				}
			}
			
			log.info("Load {} content rules, keywords contained: {}", rules.size(), keyWordList.toString());
		}
	}
}
