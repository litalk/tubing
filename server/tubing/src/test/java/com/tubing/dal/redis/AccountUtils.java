package com.tubing.dal.redis;

import com.tubing.dal.model.Account;

public class AccountUtils {

    public static Account create() {

        return new Account(
                "access-token-test",
                "refresh-token-test",
                "krovi@gmail.com",
                "krovi@sumsum-street",
                "Krovi",
                "Krov",
                "Ben-Krovim",
                "http://www.groverim.com/1",
                "english");
    }
}
