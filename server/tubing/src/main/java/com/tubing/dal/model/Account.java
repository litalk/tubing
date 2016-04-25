package com.tubing.dal.model;

import com.tubing.logic.UIDGenerator;

public class Account implements Entity {
    
    public static final String TYPE = "account";

    private String _accessToken;
    private String _refreshToken;
    private String _email;
    private String _userId;
    private String _name;
    private String _givenName;
    private String _familyName;
    private String _pictureUrl;
    private String _locale;

    private Account() {}
    
    public Account(String accessToken,
                   String refreshToken,
                   String email,
                   String userId,
                   String name,
                   String givenName,
                   String familyName,
                   String pictureUrl,
                   String locale) {

        _accessToken = accessToken;
        _refreshToken = refreshToken;
        _email = email;
        _userId = userId;
        _name = name;
        _givenName = givenName;
        _familyName = familyName;
        _pictureUrl = pictureUrl;
        _locale = locale;
    }

    public String getAccessToken() {

        return _accessToken;
    }

    public String getRefreshToken() {

        return _refreshToken;
    }

    public String getEmail() {

        return _email;
    }
    
    public String getUserId() {
        
        return _userId;
    }

    public String getName() {

        return _name;
    }

    @Override
    public String getUniqueId() {

        return UIDGenerator.generate(Account.TYPE, getUserId());
    }
    
    @Override
    public String toString() {
        
        return String.format("userId: %s", getUserId());
    }
}
