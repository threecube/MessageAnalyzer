/**
 * 
 */
package com.threecube.messageAnalyzer.spout;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import com.threecube.messageAnalyzer.parser.ParserInterface;

/**
 * @author wenbin_dwb
 *
 */
public abstract class AbstractIRichSpout implements IRichSpout{

	protected ParserInterface parserInterface;

	public void ack(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	public void fail(Object arg0) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see backtype.storm.spout.ISpout#activate()
	 */
	public void activate() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see backtype.storm.spout.ISpout#deactivate()
	 */
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#getComponentConfiguration()
	 */
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
