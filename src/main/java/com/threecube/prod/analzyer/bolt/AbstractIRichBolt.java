/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public abstract class AbstractIRichBolt implements IRichBolt{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2793902694913406515L;

	protected transient OutputCollector collector;
	
	protected Map stormConf;
	
	protected TopologyContext topologyContext;
	
	/**
	 * fields for grouping event in epl
	 */
	@Getter @Setter
	protected String[] groupFields;
	
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.topologyContext = context;
		this.collector = collector;
	}
	
	/* (non-Javadoc)
	 * @see backtype.storm.task.IBolt#cleanup()
	 */
	public void cleanup() {
	}

	/* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#getComponentConfiguration()
	 */
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
}
