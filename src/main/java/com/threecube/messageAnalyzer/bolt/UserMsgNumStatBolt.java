/**
 * 
 */
package com.threecube.messageAnalyzer.bolt;

import java.util.Date;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 * 统计来自过去一段时间来自每一个用户的消息总数，
 * 
 * @author wenbin_dwb
 *
 */
@SuppressWarnings("serial")
public class UserMsgNumStatBolt extends AbstractIRichBolt {

	private transient EPServiceProvider epServiceProvider = null;

	public void execute(Tuple arg0) {
		// TODO Auto-generated method stub
		
	}

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
	}
}
