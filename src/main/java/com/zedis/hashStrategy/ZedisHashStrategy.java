package com.zedis.hashStrategy;

import com.zedis.conf.ZedisShardInfo;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Hashing;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 *
 * 哈希策略
 */
public interface ZedisHashStrategy {
    public Hashing hashing(final List<JedisShardInfo> shards);
}
