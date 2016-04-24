package com.tubing.dal.redis;

import com.tubing.TubingApplicationTests;
import com.tubing.common.ObjectMapperUtils;
import com.tubing.dal.DALClient;
import com.tubing.dal.EntityPersister;
import com.tubing.dal.model.Account;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

/**
 * Created by kornfeld on 24/04/2016.
 */
@ContextConfiguration(locations = {
        "classpath:jedis-app-context.xml"})
public class TestEntityPersister extends TubingApplicationTests {

    @Autowired
    EntityPersister _persister;
    @Autowired
    DALClient _client;

    @Test
    public void testSetAndGet() {

        Account account = new Account("lital", "auth-code-123");
        _persister.insert(account);
        Map<String, String> fields = _client.hgetAll(account.getUniqueId());
        Account account1 = ObjectMapperUtils.to(ObjectMapperUtils.from(fields), Account.class);
        Assert.assertEquals("lital", account1.getUserId());
        Assert.assertEquals("auth-code-123", account1.getToken());
    }
}
