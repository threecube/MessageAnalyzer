/**
 * 
 */
package com.threecube.prod.analzyer.model;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.alibaba.fastjson.JSONObject;
import com.threecube.prod.analzyer.util.PropertiesUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * scheme for converting data from kafka to tuple
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class ChatMessageScheme implements Scheme{
	
	@Override
	public List<Object> deserialize(ByteBuffer ser) {
		
		Charset charset = Charset.forName( "UTF-8" );
		CharsetDecoder decoder = charset.newDecoder();
		
		try {
			
			String chatMessage = decoder.decode(ser).toString();
			
			log.info("Receive tuple from kafka, {}.", chatMessage);
			
			ChatMessageModel messageModel = JSONObject.parseObject(chatMessage, ChatMessageModel.class);
			
			if(messageModel != null) {
				String sender = messageModel.getSender();
				String senderBareJid = null;
				if(StringUtils.isNotBlank(sender)) {
					senderBareJid = StringUtils.split(sender, "/")[0];
				}
				
				String accepter = messageModel.getAccepter();
				String accepterBareJid = null;
				if(StringUtils.isNotBlank(accepter)) {
					accepterBareJid = StringUtils.split(accepter, "/")[0];
				}
				
				String subject = messageModel.getSubject();
				String type = messageModel.getType();
				String msgType = messageModel.getMsgtype();
				String message = messageModel.getMessage();
				if(!StringUtils.isEmpty(senderBareJid) && !StringUtils.isEmpty(accepterBareJid) && 
						StringUtils.equals(type, "chatMessage")){
					log.info("Emit tuple, message from {} to {}", sender, accepter);
					return new Values(senderBareJid, accepterBareJid, subject, type, msgType, message);
				}
				
			}
			
		} catch (Exception e) {
			return null; 
		}
		return null;
	}

	@Override
	public Fields getOutputFields() {
		
		return new Fields(PropertiesUtil.TupleFields);
	}

}
