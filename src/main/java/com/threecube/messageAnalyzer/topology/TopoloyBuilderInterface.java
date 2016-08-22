package com.threecube.messageAnalyzer.topology;

import backtype.storm.topology.TopologyBuilder;

/**
 * Created by wenbin_dwb on 16/8/22.
 *
 * Interface for building storm topology
 */
public interface TopoloyBuilderInterface {

    public TopologyBuilder create();
}
