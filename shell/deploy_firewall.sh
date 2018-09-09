#!/bin/sh

# jar file of messages firewall system
FIREWALL_JAR=message_firewall.jar
# main class of messages firewall system
FIREWALL_MAIN=com.threecube.prod.analzyer.topology.TopologySubmitor
# topology name of messages firewall system
FIREWALL_TOPOLOGY_NAME=message-analyzer

PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`

# Set STORM_HOME 
STORM_HOME=$(cd "$PRGDIR/.." >/dev/null; pwd)

# GET running topology list
echo "Now get all topologies runnning on this storm ......"
if [ -r "$STORM_HOME/bin/storm" ]; then
    TOPLOGY_LIST=$($STORM_HOME/bin/storm list)
else
   echo "Failed to found storm command".
   exit 22
fi

# Check if topology "message-analyzer" is running 
IS_RUNNING=$(echo $TOPLOGY_LIST | grep "${FIREWALL_TOPOLOGY_NAME}")
if [[ "$IS_RUNNING" != "" ]]; then
    echo "$TOPLOGY_LIST"
    echo "Topology $FIREWALL_TOPOLOGY_NAME is running."
    echo "Now stop $FIREWALL_TOPOLOGY_NAME ......"
    $STORM_HOME/bin/storm kill $FIREWALL_TOPOLOGY_NAME
else
   echo "No $FIREWALL_TOPOLOGY_NAME is running."
fi

# deploy $FIREWALL_TOPOLOGY_NAME topology
echo "Now start topology $FIREWALL_TOPOLOGY_NAME ......"
$STORM_HOME/bin/storm jar $STORM_HOME/$FIREWALL_JAR $FIREWALL_MAIN

