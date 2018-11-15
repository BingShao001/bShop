package com.ice.bshop;

import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class JedisClusterSource {
    protected String redisList;
    protected Set<HostAndPort> jedisClusterNodes;

    public Set<HostAndPort> getJedisClusterNodes() {
        return jedisClusterNodes;
    }

    public JedisClusterSource setJedisClusterNodes(Set<HostAndPort> jedisClusterNodes) {
        this.jedisClusterNodes = jedisClusterNodes;
        return this;
    }

    public String getRedisList() {
        return redisList;
    }

    public JedisClusterSource setRedisList(String redisList) {
        this.redisList = redisList;
        if (!StringUtils.isEmpty(redisList)) {
            this.jedisClusterNodes = new HashSet<HostAndPort>();
            String[] hostAndPortList = redisList.split(",");
            for (String hostAndPortStr : hostAndPortList) {
               String[] hostAndPort = hostAndPortStr.split(":");
               String host = hostAndPort[0];
               int port = Integer.parseInt(hostAndPort[1]);
               this.jedisClusterNodes.add(new HostAndPort(host,port));
            }
        }
        return this;
    }
}
