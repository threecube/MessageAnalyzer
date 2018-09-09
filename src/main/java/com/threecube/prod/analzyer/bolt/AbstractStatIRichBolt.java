/**
 * 
 */
package com.threecube.prod.analzyer.bolt;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Tuple;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.threecube.prod.analzyer.model.TupleModel;
import com.threecube.prod.analzyer.util.EplField;
import com.threecube.prod.analzyer.util.PropertiesUtil;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dingwenbin
 *
 */
@Slf4j
public abstract class AbstractStatIRichBolt extends AbstractIRichBolt{
	
	
	/**
	 * epl context
	 */
	@Setter
	protected String eplContext;
	
	/**
	 * epl
	 */
	@Setter
	protected String epl;
	
	/**
	 * partition fields number used in epl, please check {@link epl.properties}
	 */
	private int eplPartitionFieldNum;
	
	/**
	 * event bean used in epl, like {@code "select .. from $eventBean"}
	 */
	private Class eplEventBean;
	
	/**
	 * esper service provider
	 */
	protected transient EPServiceProvider epServiceProvider; 
	
	/**
	 * fields used by epl to doing partition
	 */
	protected String[] eplPartitionFields;
	
	/**
	 * Initialize parameters used by esper
	 * 
	 * @param eplPartitionFieldNum : partition field number used in epl
	 * @param eplEventBean : event bean used in epl, type is child class of {@code TupleModel}
	 */
	AbstractStatIRichBolt(int eplPartitionFieldNum, Class eplEventBean) {
		this.eplPartitionFieldNum = eplPartitionFieldNum;
		this.eplEventBean = eplEventBean;
	}
	
	/**
	 * process tuple from upstream bolt/spout of storm topology
	 */
	public void execute(Tuple tuple) {
		
		TupleModel model = createModelFromTuple(tuple);
		
		if(model != null) {
			epServiceProvider.getEPRuntime().sendEvent(model);
		}
		
		collector.ack(tuple);
	}

	/**
	 * prepare storm bolt, and prepare epl and epl context
	 */
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		
		super.prepare(arg0, arg1, arg2);
		this.prepareBolt();
		
		Field[] fields =  eplEventBean.getDeclaredFields();
		
		//now max count of fields used to doing partition by epl is 2, 
		eplPartitionFields = new String[2];
		for(Field field : fields) {
			EplField eplField = field.getAnnotation(EplField.class);
			
			if(eplField != null) {
				eplPartitionFields[eplField.id()] = eplField.name();
			}
		}
		
		switch(eplPartitionFieldNum) {
			case 0: {
				epl = String.format(epl, eplEventBean.getName(), PropertiesUtil.EsperWindowSize);
				break;
			}
			case 1: {
				if(StringUtils.isNotBlank(eplContext)) {
					eplContext = String.format(eplContext, eplPartitionFields[0], eplEventBean.getName());
				}
				epl = String.format(epl, eplPartitionFields[0], eplEventBean.getName(), 
						PropertiesUtil.EsperWindowSize, eplPartitionFields[0]);
				break;
			}
			case 2: {
				if(StringUtils.isNotBlank(eplContext)) {
					eplContext = String.format(eplContext, eplPartitionFields[0], eplPartitionFields[1], 
							eplEventBean.getName());
				}
				epl = String.format(epl, eplPartitionFields[0], eplPartitionFields[1], eplEventBean.getName(), 
						PropertiesUtil.EsperWindowSize, eplPartitionFields[0], eplPartitionFields[1]);
				break;
			}
		}
		
		log.info("Success to prepare esper context [{}]", eplContext);
		
		log.info("Success to prepare epl [{}]", epl);
		
		//create esper provider
		createEpProvider();
	}
	
	/**
	 * create esper provider
	 */
	protected void createEpProvider() {
		epServiceProvider = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epServiceProvider.getEPAdministrator();
		
		try {
			if(eplContext != null) {
				admin.createEPL(eplContext);
			}
			
			EPStatement statement = admin.createEPL(epl);
			statement.addListener(new UpdateListener() {

				public void update(EventBean[] newEvents, EventBean[] oldEvents) {
					
					epEventProcess(newEvents, oldEvents);
				}
			});
		} catch (EPException e) {
			
			log.error("Esper error", e);
		}
	}
	
	/**
	 * prepare bolt, 
	 * <p>this method is used for some object must be initialized in prepare 
	 */
	protected void prepareBolt() {
		
	};
	
	/**
	 * create object from tuple, which will be sent to esper,
	 * <p> Just get values of properties you use from tuple, and set into TupleModel object
	 * 
	 * @param tuple
	 * @return
	 */
	protected abstract TupleModel createModelFromTuple(Tuple tuple);
	
	/**
	 * Esper result processor, 
	 * <p> Please check epl in {@code epl.properties} your bolt uses
	 * and get values from newEvents according on the epl
	 * 
	 * @param newEvents
	 * @param oldEvents
	 */
	protected abstract void epEventProcess(EventBean[] newEvents, EventBean[] oldEvents);
}
