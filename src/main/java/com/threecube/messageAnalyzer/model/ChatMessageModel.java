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
	 * ��Ϣ�����ڵ��û�
	 * */
	private String userFrom;
	
	/**
	 * ��Ϣ�������û�
	 * */
	private String userTo;
	
	/**
	 * ��Ϣ����
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
