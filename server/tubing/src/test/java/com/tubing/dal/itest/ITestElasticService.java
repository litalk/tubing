package com.tubing.dal.itest;

import com.tubing.dal.ElasticService;
import com.tubing.dal.model.Account;
import com.tubing.logic.AccountLogic;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

@Ignore  // Integration Tests - Elasticsearch needed
public class ITestElasticService {

    @Test
    public void testSearch() throws Exception {

        Account krovi = new Account(String.format("krovi_%s@gmail.com", UUID.randomUUID()), "Krovi", "1a");
        ElasticService.insert(krovi);
        Thread.currentThread().sleep(1000);  // let elasticsearch update index with new account
        Account search = ElasticService.search(Account.class, Account.TYPE, AccountLogic.getSearchByEmailQuery(krovi));
        Assert.assertEquals(krovi.getEmail(), search.getEmail());
    }
}