/**
 * 
 */
package com.threecube.messageAnalyzer.util;

import org.apache.commons.lang.StringUtils;

import com.threecube.messageAnalyzer.model.ChatMessageModel;

/**
 * chat消息解析器，负责将从kafka接收到的消息解析成对象
 * 
 * @author wenbin_dwb
 *
 */
public class ChatMessageParseUtils {
	
	public static ChatMessageModel parse (String message) {
		if(StringUtils.isEmpty(message)){
			return null;
		}
		
		ChatMessageModel model =  new ChatMessageModel();
		
		return model;
	}
}
