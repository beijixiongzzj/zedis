package com.zedis.hashStrategy.impl;

import com.zedis.conf.ZedisShardInfo;
import com.zedis.hashStrategy.ZedisHashStrategy;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/15.
 *
 * 针对自增id做key的hash策略
 */
public class AutoIncreateIdHashStrategy extends ZedisHashStrategy{
    public int findShardNumForKey(byte[] key, List<ZedisShardInfo> shards, Map<Integer, ZedisShardInfo> shardDict) {
        String keys = new String(key);
        //long keyint = Crc32Util.crc32Str(keys);
        int keyint = Integer.valueOf(keys);
        int shardnum = keyint%shards.size();
        ZedisShardInfo shardInfo = shardDict.get(shardnum);
        return shards.indexOf(shardInfo);
    }
}
