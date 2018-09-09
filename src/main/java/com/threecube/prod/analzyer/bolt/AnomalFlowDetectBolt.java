/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.threecube.prod.analzyer.datasource.TableMapperEnum;
import com.threecube.prod.analzyer.datasource.Do.GwStatisticDO;
import com.threecube.prod.analzyer.datasource.Do.GwUserFilterDO;
import com.threecube.prod.analzyer.model.DetectResultModel;
import com.threecube.prod.analzyer.service.GwDetectorInterface;
import com.threecube.prod.analzyer.util.GwUserFilterConvetor;
import com.threecube.prod.analzyer.util.StatDataConvertor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * bolt for detecting anomal traffic
 * 
 * <p> this bolt's tuples are from Sender2AcepterMsgStatBolt and SenderMsgStatBolt
 * <p> more details about this bolt can be found from {@code anomalFlowDetectBoltDetail}
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class AnomalFlowDetectBolt extends AbstractIRichBolt{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5406599490709612167L;
	
	/**
	 * detetcor
	 */
	@Setter
	private GwDetectorInterface gwDetectorHandler;
	
	/**
	 * stream id for tuples to redis bolt
	 */
	private final String REDIS_STREAM_ID = "redis-common-stream";
	
	/**
	 * stream id for tuples to jdbc bolt
	 */
	private final String JDBC_STREAM_ID = "jdbc-common-stream";
	
	/**
	 * 
	 * @param gwDetectorHandler
	 */
	public AnomalFlowDetectBolt(GwDetectorInterface gwDetectorHandler) {
		this.gwDetectorHandler = gwDetectorHandler;
	}
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		super.prepare(stormConf, context, collector);
	};
	
	@Override
	public void execute(Tuple input) {
		
		String sender = input.getStringByField("sender");
		String accepter = input.getStringByField("accepter");
		String dimension = input.getStringByField("dimension");
		int count = input.getIntegerByField("count");
		
		// generate statModel
		GwStatisticDO gwFlowStatDO = StatDataConvertor.genModelA(sender, accepter, dimension, count);
				
		//3. detect data
		DetectResultModel detectResult = gwDetectorHandler.detect(gwFlowStatDO);
	
		//4. post process
		if(detectResult != null) {
			
			//emit statistic data to mysql bolt
			this.collector.emit(JDBC_STREAM_ID, new Values(TableMapperEnum.GW_STATTSTIC, gwFlowStatDO));
			
			if(detectResult.isValid()) {
				
				// emit tuple to redis bolt
				this.collector.emit(REDIS_STREAM_ID, new Values(detectResult.getAnormalUserId(), 
						gwFlowStatDO.getAccepter(), detectResult.getOperationCode(),
						detectResult.getRuleType()));
			}
			
			//send filter result
			GwUserFilterDO gwUserFilterDO = GwUserFilterConvetor.Model2DO(detectResult, gwFlowStatDO);
			this.collector.emit(JDBC_STREAM_ID, new Values(TableMapperEnum.GW_USER_FILTER, gwUserFilterDO));
		}
	}
 
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//declare output stream to redis
		declarer.declareStream(REDIS_STREAM_ID, new Fields("sender", "accepter", "operation", "ruletype"));
		
		//declare output stream to jdbc
		declarer.declareStream(JDBC_STREAM_ID,new Fields("tableMapper", "data"));
	}

}
