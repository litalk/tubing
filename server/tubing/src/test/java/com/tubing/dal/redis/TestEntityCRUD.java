package com.tubing.dal.redis;

import com.tubing.TubingApplicationTests;
import com.tubing.dal.EntityFetcher;
import com.tubing.dal.EntityPersister;
import com.tubing.dal.model.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by kornfeld on 24/04/2016.
 */
@ContextConfiguration(locations = {
        "classpath:jedis-app-context.xml"})
public class TestEntityCRUD extends TubingApplicationTests {

    @Autowired
    private EntityPersister _persister;
    @Autowired
    private EntityFetcher _fetcher;

    private Account _account = new Account("lital", "auth-code-123");

    @After
    public void cleanup() {

        _persister.delete(_account.getUniqueId());
    }

    @Test
    public void testInsertAndGet() {

        System.out.println("Persisting account: " + _account);
        _persister.insert(_account);
        Account fetchedAccount = _fetcher.get(_account.getUniqueId(), Account.class);
        Assert.assertEquals(_account.getUserIdToken(), fetchedAccount.getUserIdToken());
        Assert.assertEquals(_account.getAuthCode(), fetchedAccount.getAuthCode());
    }

    @Test
    public void testEntityDoesNotExist() {

        Account fetchedAccount = _fetcher.get("non-exist-entity", Account.class);
        Assert.assertEquals(null, fetchedAccount);
    }
}
