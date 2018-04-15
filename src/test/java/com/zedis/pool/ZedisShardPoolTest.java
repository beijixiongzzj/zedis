package com.zedis.pool;

import com.zedis.conf.ZedisShardInfo;
import com.zedis.exception.ZedisShardInfoException;
import com.zedis.hashStrategy.ZedisHashStrategy;
import com.zedis.hashStrategy.impl.ModeHashStrategy;
import com.zedis.hashStrategy.impl.RandomHashStrategy;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/4/13.
 */
public class ZedisShardPoolTest {
    @Test
    public void testPool() throws ZedisShardInfoException {
        List<ZedisShardInfo> shards = new ArrayList<ZedisShardInfo>();
        ZedisShardInfo info = new ZedisShardInfo("127.0.0.1",6379,2);
        ZedisShardInfo info2 = new ZedisShardInfo("127.0.0.1",6380,0);
        ZedisShardInfo info3 = new ZedisShardInfo("127.0.0.1",6381,3);
        ZedisShardInfo info4 = new ZedisShardInfo("127.0.0.1",6382,1);
        shards.add(info);
        shards.add(info2);
        shards.add(info3);
        shards.add(info4);
        ZedisHashStrategy hashStrategy = new ModeHashStrategy();
        ZedisShardPool shardedJedisPool = new ZedisShardPool(new JedisPoolConfig(),shards,new RandomHashStrategy());
        int i = 0;
        while(true){
            ShardedJedis jedis = null;
            try {
                jedis = shardedJedisPool.getPool().getResource();
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
