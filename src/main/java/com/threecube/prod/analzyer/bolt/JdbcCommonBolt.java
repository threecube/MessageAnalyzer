/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.storm.jdbc.bolt.AbstractJdbcBolt;
import org.apache.storm.jdbc.common.Column;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import com.esotericsoftware.minlog.Log;
import com.threecube.prod.analzyer.datasource.TableMapperEnum;
import com.threecube.prod.analzyer.datasource.Do.GwStatisticDO;
import com.threecube.prod.analzyer.datasource.Do.GwUserFilterDO;
import com.threecube.prod.analzyer.datasource.mapper.GwStatisticMapper;
import com.threecube.prod.analzyer.datasource.mapper.GwUserFilterMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * bolt for jdbc to insert tuples into mysql.
 * 
 * <p> this bolt's tuples are from AnomalFlowDetectBolt's stream jdbc-common-stream
 * <p> more details about this bolt can be found from {@code jdbcCommonBoltDetail}
 * 
 * @author dingwenbin
 *
 */
@Slf4j
public class JdbcCommonBolt extends AbstractJdbcBolt {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1194753693885602270L;

	/**
	 * 
	 * @param connectionProvider
	 */
	public JdbcCommonBolt(ConnectionProvider connectionProvider) {
    	
        super(connectionProvider);
    }
	
    public JdbcCommonBolt withQueryTimeoutSecs(int queryTimeoutSecs) {
        this.queryTimeoutSecs = queryTimeoutSecs;
        return this;
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        super.prepare(map, topologyContext, collector);
    }

    @Override
    public void execute(Tuple tuple) {
    	
        try {
        	TableMapperEnum tableMapper = (TableMapperEnum) tuple.getValueByField("tableMapper");
        	Object data = tuple.getValueByField("data");
            
            if(tableMapper != null && data != null) {
            	
            	List<List<Column>> columnLists = genColumnList(tableMapper, data); 
            	
            	if(this.jdbcClient != null) {
            		
            		log.info("Insert into table {} data:{}", tableMapper.getTableName(), data.toString());
            		this.jdbcClient.insert(tableMapper.getTableName(), columnLists);
            	} else {
            		log.error("Faild to insert into data, jdbcClient is null");
            	}
            }
            
            this.collector.ack(tuple);
        } catch (Exception e) {
            this.collector.reportError(e);
            this.collector.fail(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
    
    /**
     * construct columns which can be inserted into db from tuple data 
     * 
     * @param tableMapper
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
	private  List<List<Column>> genColumnList(TableMapperEnum tableMapper, Object data) {
    	
    	 List<List<Column>> columnLists = new ArrayList<List<Column>>();
    	 
    	 try {
    		 List<Column> columns = new ArrayList<>();
    		 
    		 switch(tableMapper) {
    		 	case GW_STATTSTIC: {
    		 		GwStatisticDO flowStatDataDO = (GwStatisticDO) data;
    		 		Field[] fields = GwStatisticDO.class.getDeclaredFields();
    		 		GwStatisticMapper mapper = null;
           		 
    		 		for(Field field : fields) {
    		 			field.setAccessible(true);
    		 			mapper = GwStatisticMapper.getByPropName(field.getName());
    		 			columns.add(new Column(mapper.getColumnName(), field.get(flowStatDataDO), mapper.getColumnType()));
    		 		}
    		 		
    		 		break;
    		 	}
    		 	case GW_USER_FILTER: {
    		 		GwUserFilterDO gwUserFilterDO = (GwUserFilterDO) data;
    		 		Field[] fields = GwUserFilterDO.class.getDeclaredFields();
    		 		GwUserFilterMapper mapper = null;
           		 
    		 		for(Field field : fields) {
    		 			field.setAccessible(true);
    		 			mapper = GwUserFilterMapper.getByPropName(field.getName());
    		 			columns.add(new Column(mapper.getColumnName(), field.get(gwUserFilterDO), mapper.getColumnType()));
    		 		}
    		 		
    		 		break;
    		 	}
    		 	default: {
    		 		break;
    		 	}
    		 }
    		 
    		 columnLists.add(columns);
    		 
    	 } catch(Exception e) {
    		 Log.error("Failed to convert model to tuple", e);
    	 }
    	 
    	 return columnLists;
    }
}
