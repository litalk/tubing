package com.tubing.dal.redis;

import redis.clients.jedis.Jedis;

import com.tubing.dal.DALClient;

import java.util.Map;

public class RedisClient implements DALClient {
    
    private final Jedis _jedis;
    
    private RedisClient(String host, int port) {
        
        _jedis = new Jedis(host, port);
    }
    
    public void set(String key, String value) {
        
        _jedis.set(key, value);
    }
    
    public String get(String key) {
        
        return _jedis.get(key);
    }

    public void hmset(String key, Map<String, String> fieldToValues) {

        _jedis.hmset(key, fieldToValues);
    }

    public Map<String, String> hgetAll(String key) {

        return _jedis.hgetAll(key);
    }
}
