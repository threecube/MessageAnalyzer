/**
 * 
 */
package com.threecube.messageAnalyzer.util;

import org.apache.commons.lang.StringUtils;

import com.threecube.messageAnalyzer.model.ChatMessageModel;

/**
 * chat��Ϣ�����������𽫴�kafka���յ�����Ϣ�����ɶ���
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
