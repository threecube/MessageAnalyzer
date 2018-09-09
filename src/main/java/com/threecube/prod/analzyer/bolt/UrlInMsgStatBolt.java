/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.espertech.esper.client.EventBean;
import com.threecube.prod.analzyer.enums.RuleTypeEnum;
import com.threecube.prod.analzyer.model.ChatContentModel;

import lombok.extern.slf4j.Slf4j;

/**
 * Count statistic of messages which include url,
 * 
 * the format of result is List<({sender-x}, host, count of host)>
 * 
 * <p> this bolt's tuples are from KafkaSpout.
 * <p> more details about this bolt can be found from {@code urlInMsgStatBoltDetail}.
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class UrlInMsgStatBolt extends AbstractStatIRichBolt {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9016618063669447239L;
	
	/**
	 * pattern to get host of url from message
	 */
	//private final String URL_REGEX = "(?<=//|)((\\w)+\\.)+\\w+";
	private final String URL_REGEX = "(?<=http://|https://)((\\w)+\\.)+\\w+";
	
	private Pattern UrlPattern;
	
	public UrlInMsgStatBolt() {
		super(2, ChatContentModel.class);
		
		
	}
	
	/**
	 * prepare bolt
	 */
	protected void prepareBolt() {
		
		//UrlPattern can not be init in UrlInMsgStatBolt()
		UrlPattern = Pattern.compile(URL_REGEX);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("sender", "content", "dimension", "count"));
	}
	
	@Override
	protected ChatContentModel createModelFromTuple(Tuple tuple) {
		
		String sender = tuple.getStringByField("sender");
		String message = tuple.getStringByField("message");
		
		Matcher matcher = this.UrlPattern.matcher(message);
		if(matcher.find()) {
			ChatContentModel context = new ChatContentModel();
			context.setSender(sender);
			context.setContext(matcher.group());
			
			return context;
		}
		
		//if there is not url in message body , send null
		return null;
	}
	
	@Override
	protected void epEventProcess(EventBean[] newEvents, EventBean[] oldEvents) {
		
		if (newEvents != null) {
			for (EventBean e : newEvents) {
				
				String sender = e.get(eplPartitionFields[0]).toString();
				String context = e.get(eplPartitionFields[1]).toString();
				int count = Integer.valueOf(e.get("count").toString());
				
				log.info("Statistic of urlInMsgStatBolt: {} sent {} messages including url " + context, sender, count);
				
				collector.emit(new Values(sender, context, RuleTypeEnum.URL_MSG_NUM.getCode(), count));
			}
		}
	}
}
