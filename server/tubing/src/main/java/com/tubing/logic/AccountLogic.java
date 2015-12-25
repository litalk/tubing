package com.tubing.logic;

import com.tubing.common.TubingException;
import com.tubing.dal.model.Account;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AccountLogic {

    public static String getSearchByEmailQuery(Account account) {

        try {
            return URLEncoder.encode(String.format("%s:\"%s\"", Account.EMAIL_FIELD, account.getEmail()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new TubingException(String.format("Failed to create encoded query for account: %s", account), e);
        }
    }
}
