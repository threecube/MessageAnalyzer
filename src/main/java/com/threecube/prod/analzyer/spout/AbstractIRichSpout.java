/**
 * 
 */
package com.threecube.prod.analzyer.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseRichSpout;

/**
 * abstract spout bean
 * 
 * @author dingwenbin
 *
 */
public abstract class AbstractIRichSpout extends BaseRichSpout {
	
	protected transient SpoutOutputCollector collector;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 640031064279927931L;

	/**
	 * parse message
	 * 
	 * @param message
	 * @return
	 */
	public abstract <T> T takeMessage();
	
	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#ack(java.lang.Object)
	 */
	public void ack(Object arg0) {
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#activate()
	 */
	public void activate() {
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#close()
	 */
	public void close() {
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#deactivate()
	 */
	public void deactivate() {
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#fail(java.lang.Object)
	 */
	public void fail(Object arg0) {
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.spout.ISpout#open(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.spout.SpoutOutputCollector)
	 */
	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	/* (non-Javadoc)
	 * @see org.apache.storm.topology.IComponent#getComponentConfiguration()
	 */
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
