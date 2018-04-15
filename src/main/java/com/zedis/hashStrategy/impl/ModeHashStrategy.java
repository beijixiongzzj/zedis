package com.zedis.hashStrategy.impl;

import com.zedis.conf.ZedisShardInfo;
import com.zedis.hashStrategy.ZedisHashStrategy;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Administrator on 2018/4/13.
 */
public class ModeHashStrategy extends ZedisHashStrategy {

    public int findShardNumForKey(byte[] key, List<ZedisShardInfo> shards, Map<Integer, ZedisShardInfo> shardDict) {
        String keys = new String(key);
        int keyint = Integer.parseInt(keys);
        int shardnum = keyint%shards.size();
        ZedisShardInfo shardInfo = shardDict.get(shardnum);
        return shards.indexOf(shardInfo);
    }
}
