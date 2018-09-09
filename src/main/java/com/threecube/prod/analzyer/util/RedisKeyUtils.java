/**
 * 
 */
package com.threecube.prod.analzyer.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author dingwenbin
 *
 */
public class RedisKeyUtils {

	/**
	 * redis key suffix for filter words
	 */
	private static final String GW_FILTER_WORDS = "_GW_WORDS_EX";
	
	/**
	 * redis key suffix for filter user
	 */
	private static final String GW_FILTER_USERS = "_GW_USERS_EX";
	
	private static final String GW_SYSTEM_LIMIT = "GW_SYSTEM_LIMIT";
	
	private static final String SPLIT_STR = "+";
	
	/**
	 * generate filter words redis key for user
	 * @param jid
	 * @return
	 */
	public static String genFilterWordKey(String jid) {
		
		if(StringUtils.isBlank(jid)) {
			return GW_FILTER_WORDS;
		}
		
		return jid + GW_FILTER_WORDS;
	}
	
	/**
	 * generate filter user redis key for user
	 * 
	 * @param jid
	 * @return
	 */
	public static String genFilterUserKey(String sender, String accepter) {
		
		if(StringUtils.isBlank(sender)) {
			return GW_SYSTEM_LIMIT;
		}
		
		if(StringUtils.isBlank(accepter)) {
			return sender + GW_FILTER_USERS;
		} else {
			return sender + SPLIT_STR + accepter + GW_FILTER_USERS;
		}
	}
}
