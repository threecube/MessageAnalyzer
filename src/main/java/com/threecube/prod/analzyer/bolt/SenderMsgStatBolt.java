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
 * Statistic of messages count from sender, 
 * the format of result is List<({sender-x}, message count)>
 * 
 * <p> this bolt's tuples are from KafkaSpout.
 * <p> more details about this bolt can be found from {@code senderMsgStatBoltDetail}.
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class SenderMsgStatBolt extends AbstractStatIRichBolt {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9016618063669447239L;
	
	public SenderMsgStatBolt() {
		
		super(1, ChatMessageModel.class);
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("sender", "accepter", "dimension", "count"));
	}
	
	@Override
	protected ChatMessageModel createModelFromTuple(Tuple tuple) {
		
		ChatMessageModel model = new ChatMessageModel();
		model.setSender(tuple.getStringByField("sender"));
		return model;
	}
	
	@Override
	protected void epEventProcess(EventBean[] newEvents, EventBean[] oldEvents) {
		
		if (newEvents != null) {
			for (EventBean e : newEvents) {
				String sender = e.get(eplPartitionFields[0]).toString();
				int count = Integer.valueOf(e.get("count").toString());
				
				log.info("Statistic of senderMsgStatBolt: {} sent {} messages", sender, count);
				
				collector.emit(new Values(sender, null, RuleTypeEnum.ONE_2_MANY_MSG_NUM.getCode(), count));
				
			}
		}
	}
}
