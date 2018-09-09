/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.espertech.esper.client.EventBean;
import com.threecube.prod.analzyer.enums.RuleTypeEnum;
import com.threecube.prod.analzyer.model.ChatMessageModel;

import lombok.extern.slf4j.Slf4j;

/**
 * statistic of how many accepters who sender sent messages to   
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class AccepterCountStatBolt extends AbstractStatIRichBolt {
	
	public AccepterCountStatBolt() {
		
		super(1, ChatMessageModel.class);
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("sender", "accepter", "dimension", "count"));
	}

	@Override
	protected void epEventProcess(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			for (EventBean e : newEvents) {
				String sender = e.get(eplPartitionFields[0]).toString();
				int count = Integer.valueOf(e.get("count").toString());
		
				log.info("Statistic of accepterCountStatBolt: {} sent messages to {} accepters", sender, count);
				
				collector.emit(new Values(sender, null, RuleTypeEnum.CONN_RECEIVER_NUM.getCode(), count));
			}
		}
	}

	@Override
	protected ChatMessageModel createModelFromTuple(Tuple tuple) {
		ChatMessageModel model = new ChatMessageModel();
		model.setSender(tuple.getStringByField("sender"));
		model.setAccepter(tuple.getStringByField("accepter"));
		return model;
	}

}
