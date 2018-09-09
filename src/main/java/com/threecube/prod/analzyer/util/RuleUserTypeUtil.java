/**
 * 
 */
package com.threecube.prod.analzyer.util;

import org.apache.commons.lang3.StringUtils;

import com.threecube.prod.analzyer.enums.RuleUserEnum;

/**
 * @author dingwenbin
 *
 */
public class RuleUserTypeUtil {
	
	/**
	 * prefix of buyer jid
	 */
	private static final String PREFIX_BUYER = "1_";
	
	/**
	 * prefix of seller jid
	 */
	private static final String PREFIX_SELLER = "2_";
	
	/**
	 * prefix of waiter jid
	 */
	private static final String PREFIX_WAITER = "3_";
	
	/**
	 * check if the rule(given by ruleUserCode) is for user(given by jid) 
	 * 
	 * @param ruleUserCode
	 * @param jid
	 * @return
	 */
	public static boolean isRuleForUser(String ruleUserCode, String jid) {
		
		if(StringUtils.isBlank(ruleUserCode) || StringUtils.isBlank(jid)) {
			return false;
		}
		
		RuleUserEnum ruleUserType = RuleUserEnum.getByCode(ruleUserCode);
		
		if(ruleUserType == null) {
			return false;
		}
		
		switch(ruleUserType) {
			case ALL:
				return true;
			case BUYER:
				return isBuyer(jid);
			case SELLER:
				return isSeller(jid);
			case BUYER_SELLER:
				return isBuyer(jid) || isSeller(jid);
			case WAITER:
				return isWaiter(jid);
			default:
				return false;	
		}
	}
	
	/**
	 * check if user is buyer by jid
	 * 
	 * @param jid
	 * @return
	 */
	private static boolean isBuyer(String jid) {
		
		return StringUtils.startsWith(jid, PREFIX_BUYER);
	}
	
	/**
	 * check if user is seller by jid
	 * 
	 * @param jid
	 * @return
	 */
	private static boolean isSeller(String jid) {
		
		return StringUtils.startsWith(jid, PREFIX_SELLER);
	}
	
	/**
	 * check if user is waiter by jid
	 * 
	 * @param jid
	 * @return
	 */
	private static boolean isWaiter(String jid) {
		
		return StringUtils.startsWith(jid, PREFIX_WAITER);
	}
}
