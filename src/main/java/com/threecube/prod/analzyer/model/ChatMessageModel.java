/**
 * 
 */
package com.threecube.prod.analzyer.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.threecube.prod.analzyer.util.EplField;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class ChatMessageModel extends TupleModel{
	
	@Getter @Setter
	private String id;
	
	/**
	 * sender jid
	 */
	@Getter @Setter
	@EplField(id=0, name="sender")
	private String sender;
	
	/**
	 * receiver jid
	 */
	@Getter @Setter
	@EplField(id=1, name="accepter")
	private String accepter;
	
	/**
	 * message type
	 */
	private String _type;
	
	/**
	 * if system message, 1:system message, 0: common chat message
	 */
	@Getter @Setter
	private String msgtype;
	
	/**
	 * subject
	 */
	@Getter @Setter
	private String subject;
	
	/**
	 * message id
	 */
	@Getter @Setter
	private int messageId;
	
	/**
	 * sequence of message 
	 */
	@Getter @Setter
	private String seq;
	
	/**
	 * client version
	 */
	@Getter @Setter
	private String version;
	
	/**
	 * message send time
	 */
	@Getter @Setter
	private String sendtime;
	
	/**
	 * sending messages spend time
	 */
	@Getter @Setter
	private String longtime;
	
	/**
	 * message body
	 */
	@Getter @Setter
	private String message;
	
	
	@JSONField(name="_type")
	public String getType() {
		return this._type;
	}
	
	@JSONField(name="_type")
	public void setType(String _type) {
		this._type = _type;
	}
}
