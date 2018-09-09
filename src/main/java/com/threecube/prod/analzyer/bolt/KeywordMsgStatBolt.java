/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.espertech.esper.client.EventBean;
import com.threecube.prod.analzyer.enums.RuleTypeEnum;
import com.threecube.prod.analzyer.model.ChatContentModel;
import com.threecube.prod.analzyer.model.TupleModel;
import com.threecube.prod.analzyer.service.MessageAnormalDetector;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Count statistic of messages which include key word(given by manually)
 *   
 * the format of result is List<({sender-x}, {key word}, message count)>
 * 
 * <p> this bolt's tuples are from KafkaSpout.
 * <p> more details about this bolt can be found from {@code keywordMsgStatBoltDetail}.
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class KeywordMsgStatBolt extends AbstractStatIRichBolt {
	
	@Setter
	private MessageAnormalDetector messageAnormalDetector;
	
	public KeywordMsgStatBolt() {
		
		super(2, ChatContentModel.class);
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("sender", "content", "dimension", "count"));
	}

	@Override
	protected TupleModel createModelFromTuple(Tuple tuple) {
		
		String sender = tuple.getStringByField("sender");
		String message = tuple.getStringByField("message");
		String keyword = messageAnormalDetector.keyWordFind(message);
		
		if(StringUtils.isBlank(keyword)) {
			return null;
		}
		
		ChatContentModel context = new ChatContentModel();
		context.setSender(sender);
		context.setContext(keyword);
		
		return context;
	}

	@Override
	protected void epEventProcess(EventBean[] newEvents, EventBean[] oldEvents) {
		
		if (newEvents != null) {
			for (EventBean e : newEvents) {
				String sender = e.get(eplPartitionFields[0]).toString();
				String keyword = e.get(eplPartitionFields[1]).toString();
				int count = Integer.valueOf(e.get("count").toString());
				
				log.info("Statistic of keywordMsgStatBolt: {} sent {} messages including keyword " + keyword, sender, count);
				
				collector.emit(new Values(sender, keyword, RuleTypeEnum.KEYWORD_MSG_NUM.getCode(), count));
			}
		}
	}

}
