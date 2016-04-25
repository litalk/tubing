package com.tubing.dal.redis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tubing.TubingApplicationTests;
import com.tubing.dal.EntityFetcher;
import com.tubing.dal.EntityPersister;
import com.tubing.dal.model.Account;

@ContextConfiguration(locations = { "classpath:jedis-app-context.xml" })
public class TestEntityCRUD extends TubingApplicationTests {
    
    @Autowired
    private EntityPersister _persister;
    @Autowired
    private EntityFetcher _fetcher;
    
    private Account _account = AccountUtils.create();
    
    @After
    public void cleanup() {
        
        _persister.delete(_account.getUniqueId());
    }
    
    @Test
    public void testInsertAndGet() {
        
        System.out.println("Persisting account: " + _account);
        _persister.insert(_account);
        Account fetchedAccount = _fetcher.get(_account.getUniqueId(), Account.class);
        Assert.assertEquals(_account.getAccessToken(), fetchedAccount.getAccessToken());
        Assert.assertEquals(_account.getRefreshToken(), fetchedAccount.getRefreshToken());
        Assert.assertEquals(_account.getUserId(), fetchedAccount.getUserId());
    }
    
    @Test
    public void testEntityDoesNotExist() {
        
        Account fetchedAccount = _fetcher.get("non-exist-entity", Account.class);
        Assert.assertEquals(null, fetchedAccount);
    }
}
