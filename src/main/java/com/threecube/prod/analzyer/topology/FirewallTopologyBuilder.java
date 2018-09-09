/**
 * 
 */
package com.threecube.prod.analzyer.topology;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import com.threecube.prod.analzyer.bolt.AbstractIRichBolt;
import com.threecube.prod.analzyer.model.BoltDetailModel;
import com.threecube.prod.analzyer.model.SpoutDetailModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingwenbin
 *
 */
public class FirewallTopologyBuilder implements TopoloyBuilderInterface{
	
	@Getter @Setter
	private String topologyName;
	
	/**
	 * information about spouts used by topology
	 */
	@Setter
	private List<SpoutDetailModel> spoutDetailModels;
	
	/**
	 * information about bolts used by topology
	 */
	@Setter
	private List<BoltDetailModel> boltDetailModels;
	
	/**
	 * create topology builder
	 */
	public TopologyBuilder buildTopology() {

		TopologyBuilder builder = new TopologyBuilder();
		
		//add spout into topology 
		for(SpoutDetailModel spoutDetail : spoutDetailModels) {
			
			builder.setSpout(spoutDetail.getId(), spoutDetail.getComponent(), Integer.valueOf(spoutDetail.getParallNum()));
		}
		
		//add bolt into topology
		for(BoltDetailModel boltDetail : boltDetailModels) {
			
			// create boltDeclarer
			BoltDeclarer boltDeclarer = builder.setBolt(boltDetail.getId(), boltDetail.getComponent(), Integer.valueOf(boltDetail.getParallNum()));
			
			Fields fields = null;
			
			if(boltDetail.getComponent() instanceof AbstractIRichBolt) {
				String[] groupFields = ((AbstractIRichBolt) boltDetail.getComponent()).getGroupFields();
				
				if(groupFields != null && groupFields.length > 0) {
					fields = new Fields(groupFields);
				}
			}
			
			Iterator<Map.Entry<String, String>> iterators = boltDetail.getUpstreamIdMap().entrySet().iterator();
			while(iterators.hasNext()) {
				Map.Entry<String, String> iterator = iterators.next();
				String componentId= iterator.getKey();
				String streamId = iterator.getValue();
				
				if(StringUtils.isBlank(componentId)) {
					continue;
				}
				
				if(fields == null) {
					
					// if there are no fields, don't grouping by fields, instead of using shuffle grouping
					if(StringUtils.isNotBlank(streamId)) {
						boltDeclarer.shuffleGrouping(componentId, streamId);
					} else {
						boltDeclarer.shuffleGrouping(componentId);
					}
				} else {
					
					//if there are fields, grouping by fields
					if(StringUtils.isNotBlank(streamId)) {
						boltDeclarer.fieldsGrouping(componentId, streamId, fields);
					} else {
						boltDeclarer.fieldsGrouping(componentId, fields);
					}
				}
			}
		}
		
		return builder;
	}
	
}
