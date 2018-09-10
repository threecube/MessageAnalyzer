# 消息分析系统
----

## 工程说明
该工程作为消息网关的一部分，通过分析用户聊天信息，发现可疑用户，将分析结果反馈给IM服务器进行禁言、预警等操作，或者反馈给管理员，由管理员手动设置进行用户禁言等。

## 流程说明

1. 从kafka中读取用户聊天消息。
2. 解析聊天消息，并通过storm spout发送到storm拓扑结构中。
3. 由统计bolt对聊天消息的各个维度进行统计，得到统计结果。
4. 统计结果由检测器进行检测，如果有异常， 则将统计数据和检测结果发送给jdbc bolt进行入库，
5. 如果需要立即生效的检测结果，则交给redis bolt直接写入redis进行生效。

## 模块说明

### storm spout
* kafkaSpout
* KafkaSpoutConfig
* ChatMessageScheme：对消息日志进行解析，过滤掉非聊天类型的数据，并将聊天消息发送给storm集群。

### storm bolt
* SenderMsgStatBolt
以消息发送者作为统计维度，统计单位时间内，某一个发送者发送的消息总量。

* Sender2AcepterMsgStatBolt
以消息发送者和消息接受者两个信息作为统计维度，统计单位时间内，某个发送者发送消息到某个接收者的消息总量。

* GrossMsgStatBolt
以整个系统接收到的消息作为统计维度，统计单位时间内，所有发送者发送的消息总量。

* AccepterCountStatBolt
以消息发送者作为统计维度，统计单位时间内，某个发送者发送消息到多少个不同的接收方。

* AnomalFlowDetectBolt 
检测器，根据统计数据和检测规则，对异常用户进行检测。

* JdbcCommonBolt
common bolt， 用于将统计结果和检测结果写入到db职工。

* RedisCommonBolt
common bolt，用户将黑名单用户写入到redis中。

## 如何运行

### 本地方式运行
本地运行***com.threecube.prod.analzyer.StormLocalService***即可。

### 集群方式运行
将打好的*message_analyzer.jar*包上传到storm集群的nimbus所在服务器上；运行如下命令：

```
> ${STORM_HOME}/bin/storm jar ${JAR_PATH}/message_analyzer.jar com.threecube.prod.analzyer.topology.TopologySubmitor
```

## 开发帮助

### 开发统计类型bolt
继承***AbstractStatIRichBolt***， 并在***application-storm-bolt.xml***中定义bolt bean， 并在***application-storm-topology.xml***中定义bolt相关的配置信息(包括运行所需资源，拓扑结构)。

### 开发spout
继承***AbstractIRichSpout***， 并在***application-storm-spout.xml***中定义bolt bean， 并在***application-storm-topology.xml***中定义spout的配置信息(包括运行所需资源，拓扑结构)。

 