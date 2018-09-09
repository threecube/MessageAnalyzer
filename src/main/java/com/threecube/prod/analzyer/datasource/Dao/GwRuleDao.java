/**
 * 
 */
package com.threecube.prod.analzyer.datasource.Dao;

import java.util.List;

import com.threecube.prod.analzyer.datasource.Do.GwRuleDO;

/**
 * @author dingwenbin
 *
 */
public interface GwRuleDao {

	public GwRuleDO selectByRuleid(int ruleId);
	
	public List<GwRuleDO> selectValid();
	
	public List<GwRuleDO> selectByRuleType(String ruleTypeCode);
}
