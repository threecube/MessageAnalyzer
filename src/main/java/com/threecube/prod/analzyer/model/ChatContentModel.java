/**
 * 
 */
package com.threecube.prod.analzyer.model;

import com.threecube.prod.analzyer.util.EplField;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class ChatContentModel extends TupleModel{
	
	@Getter @Setter
	@EplField(id=0, name="sender")
	private String sender;
	
	@Getter @Setter
	private String accepter;
	
	@Getter @Setter
	@EplField(id=1, name="context")
	private String context;
}
