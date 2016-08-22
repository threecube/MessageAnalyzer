/**
 * 
 */
package com.threecube.messageAnalyzer.parser;

import org.apache.commons.lang.StringUtils;

import com.threecube.messageAnalyzer.model.ChatMessageModel;

/**
 * chat message parser
 * 
 * @author wenbin_dwb
 *
 */
public class ChatMessageParseUtils implements ParserInterface{
	
	public ChatMessageModel parse (String message) {
		if(StringUtils.isEmpty(message)){
			return null;
		}
		
		ChatMessageModel model =  new ChatMessageModel();
		
		return model;
	}
}
