package com.tubing.dal.model;

public class Account implements Entity {
    
    public static final String TYPE = "account";

    private String _userIdToken;
    private String _authCode;

    private Account() {}
    
    public Account(String userIdToken, String authCode) {
        
        _userIdToken = userIdToken;
        _authCode = authCode;
    }
    
    public String getUserIdToken() {
        
        return _userIdToken;
    }
    
    public String getAuthCode() {
        
        return _authCode;
    }

    @Override
    public String getUniqueId() {

        return String.format("%s:%s", TYPE, _userIdToken);
    }
    
    @Override
    public String toString() {
        
        return String.format("userIdToken: %s, authCode: %s", _userIdToken, _authCode);
    }
}
