package com.zedis.pool;

import com.zedis.conf.ZedisShardInfo;
import com.zedis.exception.ZedisShardInfoException;
import com.zedis.hashStrategy.ZedisHashStrategy;
import com.zedis.hashStrategy.impl.ModeHashStrategy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */
public class ZedisShardPool {

    private ShardedJedisPool pool;

    private ZedisHashStrategy hashStrategy = new ModeHashStrategy();

    public ZedisShardPool(GenericObjectPoolConfig poolConfig, List<ZedisShardInfo> shards) throws ZedisShardInfoException {
        List<JedisShardInfo> jedisShards = transCheckShardInfo(shards);
        this.pool = new ShardedJedisPool(new JedisPoolConfig(),jedisShards,hashStrategy.hashing(shards));
    }

    private List<JedisShardInfo> transCheckShardInfo(List<ZedisShardInfo> shards) throws ZedisShardInfoException {
        if(shards == null || shards.size() == 0){
            throw new ZedisShardInfoException("not setting zedis shard info");
        }
        List<Integer> shardnums = new ArrayList<Integer>();
        for(ZedisShardInfo shardinfo:shards){
            shardnums.add(shardinfo.getShard());
        }
        Collections.sort(shardnums);
        int index = 0;
        for(int shardInfoShard:shardnums){
            if(shardInfoShard != index){
                throw  new ZedisShardInfoException("setting zedis shard num error");
            }
            index ++;
        }
        List<JedisShardInfo> ret = new ArrayList<JedisShardInfo>();
        for(ZedisShardInfo shardInfo : shards){
            ret.add(shardInfo);
        }
        return ret;
    }

    public ShardedJedisPool getPool() {
        return pool;
    }
}
