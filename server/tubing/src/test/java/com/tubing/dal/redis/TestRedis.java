package com.tubing.dal.redis;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {
    
    @Test
    public void testCreate() {
        
        Jedis jedis = new Jedis("172.17.8.51", 32768);
        jedis.set("foo", "bar");
        Assert.assertEquals("bar", jedis.get("foo"));
    }
}
