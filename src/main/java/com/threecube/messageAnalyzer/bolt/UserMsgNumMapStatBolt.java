package com.threecube.messageAnalyzer.bolt;

import java.util.Map;

import com.threecube.messageAnalyzer.model.ChatMessageModel;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;

/**
 * @author wenbin_dwb
 *
 */
public class UserMsgNumMapStatBolt extends AbstractIRichBolt {

	private transient EPServiceProvider epServiceProvider = null;
	
	/* (non-Javadoc)
	 * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
	 */
	public void execute(Tuple tuple) {
		//解析该bolt接收到的数据，并将解析后的数据投递给esper进行统计
		ChatMessageModel model = new ChatMessageModel();
		model.setUserFrom(tuple.getString(0));
		model.setUserTo(tuple.getString(1));
		model.setMessage(tuple.getString(2));
		
		epServiceProvider.getEPRuntime().sendEvent(model);
		
		collector.ack(tuple);
		
	}

	/* (non-Javadoc)
	 * @see backtype.storm.task.IBolt#prepare(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
	 */
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		super.prepare(arg0, arg1, arg2);
		//创建esper上下文，并开始接受数据
		createEpProvider();
	}

	private void createEpProvider() {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		String esb = ChatMessageModel.class.getName();
		
		String context = "create context userToReceiverStat partition by userFrom from " + esb;
		String epl = "context userToReceiverStat select userFrom, userTo, count(1) as count from "
							+ esb + ".win:time(" + STAT_WINDOWS_SIZE + " sec) group by userFrom, userTo";
		try {
			admin.createEPL(context);
			EPStatement statement = admin.createEPL(epl);
			statement.addListener(new UpdateListener() {

				public void update(EventBean[] arg0, EventBean[] arg1) {
					// TODO Auto-generated method stub
					if (arg0 != null) {
						for (EventBean e : arg0) {
							String userFrom = e.get("userFrom").toString();
							String userToString = e.get("userTo").toString();
							int count = Integer.valueOf(e.get("count").toString());
									
						}
					}
				}
			});
		} catch (EPException e) {
			e.printStackTrace();
		}
	}
}
