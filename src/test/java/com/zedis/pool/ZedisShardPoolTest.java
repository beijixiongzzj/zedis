package com.zedis.pool;

import com.zedis.conf.ZedisShardInfo;
import com.zedis.hashStrategy.ZedisHashStrategy;
import com.zedis.hashStrategy.impl.ModeHashStrategy;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/4/13.
 */
public class ZedisShardPoolTest {
    @Test
    public void testPool(){
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo info = new ZedisShardInfo("127.0.0.1",6379,2);
        JedisShardInfo info2 = new ZedisShardInfo("127.0.0.1",6380,0);
        JedisShardInfo info3 = new ZedisShardInfo("127.0.0.1",6381,3);
        JedisShardInfo info4 = new ZedisShardInfo("127.0.0.1",6382,1);
        shards.add(info);
        shards.add(info2);
        shards.add(info3);
        shards.add(info4);
        ZedisHashStrategy hashStrategy = new ModeHashStrategy();
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(new JedisPoolConfig(),shards,hashStrategy.hashing(shards));
        int i = 0;
        while(true){
            ShardedJedis jedis = null;
            try {
                jedis = shardedJedisPool.getResource();
                jedis.set(i+"",UUID.randomUUID().toString());
                System.out.println("set");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }catch (Throwable thr){
                thr.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }
            i++;
        }
    }
}
