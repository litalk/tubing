package com.tubing.dal.redis;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tubing.TubingApplicationTests;

// docker run --name tubing-redis -d -p 6379:6379 redis
// find ip using ifocnfig
//@ImportResource("classpath:jedis-app-context.xml")
@ContextConfiguration(locations = {
        "classpath:jedis-app-context.xml"})
public class TestRedis extends TubingApplicationTests {

    @Autowired
    RedisClient _client;

    @Test
    public void testSetAndGet() {

        final String VALUE = "bar";
        _client.set("foo", VALUE);
        Assert.assertEquals(VALUE, _client.get("foo"));
    }
}
