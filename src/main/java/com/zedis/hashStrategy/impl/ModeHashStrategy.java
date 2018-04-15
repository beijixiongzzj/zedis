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
public class ModeHashStrategy implements ZedisHashStrategy {
    public Hashing hashing(final List<ZedisShardInfo> shards) {
        final Map<Integer,JedisShardInfo> shardDict = new HashMap<Integer,JedisShardInfo>();
        final Map<JedisShardInfo,Integer> indexDict = new HashMap<JedisShardInfo,Integer>();
        int  i = 0;
        for(ZedisShardInfo info:shards){
            shardDict.put(info.getShard(),info);
            indexDict.put(info,i);
            i++;
        }
        return new Hashing(){

            public String getKeyTag(String key) {
                if (Sharded.DEFAULT_KEY_TAG_PATTERN != null) {
                    Matcher m = Sharded.DEFAULT_KEY_TAG_PATTERN.matcher(key);
                    if (m.find()) {
                        return m.group(1);
                    }
                }
                return key;
            }

            public long hash(String key) {
                return Hashing.MURMUR_HASH.hash(key);
            }

            public long hash(byte[] key) {
                String keys = new String(key);
                int keyint = Integer.parseInt(keys);
                int shardnum = keyint%shards.size();
                JedisShardInfo shardInfo = shardDict.get(shardnum);
                int infoIndex = indexDict.get(shardInfo);
                return Hashing.MURMUR_HASH.hash("SHARD-" + infoIndex + "-NODE-" + 1) - 1;
            }
        };
    }
}
