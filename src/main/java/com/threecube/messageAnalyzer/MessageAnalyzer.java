package com.threecube.messageAnalyzer;

import com.threecube.messageAnalyzer.kafka.KafkaSimpleReader;
import com.threecube.messageAnalyzer.topology.MessageAnalyzerTopology;
import com.threecube.messageAnalyzer.topology.TopoloyBuilderInterface;

/**
 * Created by wenbin_dwb on 16/8/14.
 */
public class MessageAnalyzer {

    public static void main(String[] args){

        //1. start thread pool to read data from kafka
        KafkaSimpleReader.startRead();

        //2. submit storm topology
        TopoloyBuilderInterface topology = new MessageAnalyzerTopology(null);
        topology.create();
    }
}
