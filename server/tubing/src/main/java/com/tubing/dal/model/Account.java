package com.tubing.dal.model;

public class Account implements Entity {
    
    public static final String TYPE = "account";

    private String _userId;
    private String _token;

    private Account() {}
    
    public Account(String userId, String token) {
        
        _userId = userId;
        _token = token;
    }
    
    public String getUserId() {
        
        return _userId;
    }
    
    public String getToken() {
        
        return _token;
    }

    @Override
    public String getUniqueId() {

        return String.format("%s:%s", TYPE, _userId);
    }
    
    @Override
    public String toString() {
        
        return String.format("userId: %s, token: %s", _userId, _token);
    }
}
