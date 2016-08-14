/**
 * 
 */
package com.threecube.messageAnalyzer.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 * @author wenbin_dwb
 *
 */
public abstract class AbstractIRichBolt implements IRichBolt{

	//统计的时间窗口，单位为秒
	protected static final int STAT_WINDOWS_SIZE = 60;
	
	protected OutputCollector collector;
	protected Map stormConf;
	protected TopologyContext context;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;

	}


	/* (non-Javadoc)
	 * @see backtype.storm.task.IBolt#cleanup()
	 */
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
	 */
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
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
