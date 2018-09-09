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
 * @author dingwenbin
 *
 */
@Slf4j
public class GrossMsgStatBolt extends AbstractStatIRichBolt {
	
	public GrossMsgStatBolt() {
		super(0, ChatMessageModel.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("sender", "accepter", "dimension", "count"));
	}

	@Override
	protected void epEventProcess(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			for (EventBean e : newEvents) {
				int count = Integer.valueOf(e.get("count").toString());
				
				log.info("Statistic of grossMsgStatBolt: system received {} messages", count);
				
				collector.emit(new Values(null, null, RuleTypeEnum.MULTI_2_MULTI_MSG_NUM.getCode(), count));
				
			}
		}
	}

	@Override
	protected ChatMessageModel createModelFromTuple(Tuple tuple) {
		return new ChatMessageModel();
	}

}
