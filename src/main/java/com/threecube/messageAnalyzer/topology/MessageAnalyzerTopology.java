package com.threecube.messageAnalyzer.topology;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.threecube.messageAnalyzer.bolt.ReceiverCountStatBolt;
import com.threecube.messageAnalyzer.bolt.UserMsgNumMapStatBolt;
import com.threecube.messageAnalyzer.bolt.UserMsgNumStatBolt;
import com.threecube.messageAnalyzer.spout.ChatMessageSpout;

/**
 * Created by wenbin_dwb on 16/8/22.
 */
public class MessageAnalyzerTopology extends AbstractTopologyBuilder {

    public MessageAnalyzerTopology(String topologyName, Config config) {
        super(topologyName, config);
    }

    public TopologyBuilder create() {
        TopologyBuilder builder = new TopologyBuilder();

        //spout bean
        builder.setSpout("kafka-message", new ChatMessageSpout(), 1);

        //bolt for total messages number statistic sent by user to receiver
        builder.setBolt("user-msg-receiver-num-stat", new UserMsgNumMapStatBolt(), 2)
        	.fieldsGrouping("kafka-message", new Fields("userFrom", "userTo"));

        //bolt for total messages number statistic sent by user
        builder.setBolt("user-msg-num-stat", new UserMsgNumStatBolt(), 2).
        	fieldsGrouping("user-msg-receiver-num-stat", new Fields("userFrom"));

        //bolt for total receiver count connected by user
        builder.setBolt("receiver-num-sent-by-user", new ReceiverCountStatBolt(), 2)
                .fieldsGrouping("kafka-message", new Fields("userFrom", "userTo"));

        return builder;
    }
}
