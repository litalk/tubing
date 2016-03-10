package com.tubing.dal.redis;

import com.tubing.dal.DALClient;
import redis.clients.jedis.Jedis;

public class RedisClient implements DALClient {

    private final Jedis _jedis;

    public RedisClient() {

        _jedis = new Jedis("172.17.8.101", 6379);
    }

    public void add(String key, String value) {

        _jedis.set(key, value);
    }

    public String get(String key) {

        return _jedis.get(key);
    }
}
