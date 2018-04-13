package com.zedis.conf;

import redis.clients.jedis.JedisShardInfo;

import java.net.URI;

/**
 *
 * Zedis的分片连接信息
 *
 * 比redis多添加了一个shard信息
 *
 * Created by Administrator on 2018/4/13.
 */
public class ZedisShardInfo extends JedisShardInfo{

    private int shard;

    public ZedisShardInfo(String host,int shard) {
        super(host);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, String name,int shard) {
        super(host, name);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, int port,int shard) {
        super(host, port);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, int port, String name,int shard) {
        super(host, port, name);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, int port, int timeout,int shard) {
        super(host, port, timeout);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, int port, int timeout, String name,int shard) {
        super(host, port, timeout, name);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, int port, int connectionTimeout, int soTimeout, int weight,int shard) {
        super(host, port, connectionTimeout, soTimeout, weight);
        this.shard = shard;
    }

    public ZedisShardInfo(String host, String name, int port, int timeout, int weight,int shard) {
        super(host, name, port, timeout, weight);
        this.shard = shard;
    }

    public ZedisShardInfo(URI uri,int shard) {
        super(uri);
        this.shard = shard;
    }

    public int getShard() {
        return shard;
    }

    public void setShard(int shard) {
        this.shard = shard;
    }
}
