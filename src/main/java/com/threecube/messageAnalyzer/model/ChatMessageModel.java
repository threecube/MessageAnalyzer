/**
 * 
 */
package com.threecube.messageAnalyzer.model;

/**
 * chat message model from kafka
 * 
 * @author wenbin_dwb
 *
 */
public class ChatMessageModel {

	/**
	 * 消息来自于的用户
	 * */
	private String userFrom;
	
	/**
	 * 消息发往的用户
	 * */
	private String userTo;
	
	/**
	 * 消息内容
	 * */
	private String message;
	
	/**
	 * @return the userFrom
	 */
	public String getUserFrom() {
		return userFrom;
	}

	/**
	 * @param userFrom the userFrom to set
	 */
	public void setUserFrom(String userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * @return the userTo
	 */
	public String getUserTo() {
		return userTo;
	}

	/**
	 * @param userTo the userTo to set
	 */
	public void setUserTo(String userTo) {
		this.userTo = userTo;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
