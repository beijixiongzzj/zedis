package com.zedis.hashStrategy.impl;

import com.zedis.conf.ZedisShardInfo;
import com.zedis.hashStrategy.ZedisHashStrategy;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/15.
 */
public class RandomHashStrategy extends ZedisHashStrategy{
    public int findShardNumForKey(byte[] key, List<ZedisShardInfo> shards, Map<Integer, ZedisShardInfo> shardDict) {
        Random random = new Random();
        return random.nextInt(shards.size());
    }
}
