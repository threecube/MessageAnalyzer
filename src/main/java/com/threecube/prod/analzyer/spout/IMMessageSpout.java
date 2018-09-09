/**
 * 
 */
package com.threecube.prod.analzyer.spout;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.threecube.prod.analzyer.model.ChatMessageModel;
import com.threecube.prod.analzyer.util.PropertiesUtil;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * spout for send messages from IM
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class IMMessageSpout extends AbstractIRichSpout {
	
	@Setter
	private String topic;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5131718271183089893L;

	/**
	 * fetch messages from queue and send to storm bolt
	 */
	public void nextTuple() {
		
		ChatMessageModel model = takeMessage();
		
		if(model != null) {
			String sender = model.getSender();
			String bareJid = null;
			if(StringUtils.isNotBlank(sender)) {
				bareJid = StringUtils.split(sender, "//")[0];
			}
			
			String accepter = model.getAccepter();
			String subject = model.getSubject();
			String type = model.getType();
			String msgType = model.getMsgtype();
			String message = model.getMessage();
			if(!StringUtils.isEmpty(sender) && !StringUtils.isEmpty(accepter)){
				log.info("emit tuple, message from {} to {}", sender, accepter);
				this.collector.emit(new Values(bareJid, accepter, subject, type, msgType, message));
			}
		}
	}
	
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(PropertiesUtil.TupleFields));
	}
	
	@SuppressWarnings({ "hiding", "unchecked" })
	@Override
	public <ChatMessageModel> ChatMessageModel takeMessage() {
		ChatMessageModel model = null;
		
		return model;
	}
}
