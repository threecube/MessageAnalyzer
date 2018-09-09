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
 * Statistic of messages count from sender to accepter, 
 * the format of result is List<({sender-x}, {accepter-y}, message count)>
 * 
 * <p> this bolt's tuples are from KafkaSpout.
 * <p> more details about this bolt can be found from {@code sender2AccepterMsgStatBoltDetail}.
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class Sender2AcepterMsgStatBolt extends AbstractStatIRichBolt {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 455528741037050413L;
	
	public Sender2AcepterMsgStatBolt() {
		
		super(2, ChatMessageModel.class);
	}
	
	@Override
	protected ChatMessageModel createModelFromTuple(Tuple tuple) {
		ChatMessageModel model = new ChatMessageModel();
		model.setSender(tuple.getStringByField("sender"));
		model.setAccepter(tuple.getStringByField("accepter"));
		return model;
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
				String accepter = e.get(eplPartitionFields[1]).toString();
				int count = Integer.valueOf(e.get("count").toString());
		
				log.info("Statistic of sender2AcepterMsgStatBolt: {} sent {} messages to " + accepter, sender, count);
				
				collector.emit(new Values(sender, accepter, RuleTypeEnum.ONE_2_ONE_MSG_NUM.getCode(), count));
			}
		}
	}
}
