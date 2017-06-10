package com.course.mvc.service.redis;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by Alexey on 10.06.2017.
 */
@Repository
public class RedisDaoImpl implements RedisDao{

    private PoolFactory poolFactory = PoolFactory.getInstance();


    @Override
    public void saveDataByKey(String key, String value) {
        poolFactory.getJedis().lpush(key,value);
    }

    @Override
    public List<String> getAllDataByKey(String key) {
        //0 - c первого елемента, -1 - до последнего
        return poolFactory.getJedis().lrange(key,0,-1);
    }
}
