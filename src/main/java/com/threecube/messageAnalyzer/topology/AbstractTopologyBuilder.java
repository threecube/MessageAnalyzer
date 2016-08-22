package com.threecube.messageAnalyzer.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

/**
 * Created by wenbin_dwb on 16/8/22.
 *
 * Abstract class to build storm topology
 */
public abstract class AbstractTopologyBuilder implements TopoloyBuilderInterface {

    /**
     * topology name
     */
    protected String topologyName;

    /**
     * topology configure
     */
    protected Config topologyConfig;


    public AbstractTopologyBuilder(String topologyName, Config topologyConfig){
        this.topologyName = topologyName;
        this.topologyConfig = topologyConfig;
    }

    /**
     * submit topology to storm cluster
     * */
    public void submitTopology() {

        TopologyBuilder topologyBuilder = create();

        if(topologyBuilder != null) {
            try{
                StormSubmitter.submitTopology(this.topologyName, this.topologyConfig, topologyBuilder.createTopology());
            }catch(Exception e){
                System.out.print("Failed to create topology " + this.topologyName);
                e.printStackTrace();
            }
        }

    }
}
