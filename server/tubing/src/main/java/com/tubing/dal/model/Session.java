package com.tubing.dal.model;

import java.util.Date;

import com.tubing.logic.UIDGenerator;

public class Session implements Entity {
    
    public static final String TYPE = "session";
    private final String _session;
    private final String _userId;
    private final Date _loginTime;
    
    public Session(String session, String userId) {
        
        _session = session;
        _userId = userId;
        _loginTime = new Date();
    }
    
    public String getSession() {
        
        return _session;
    }
    
    public String getUserId() {
        
        return _userId;
    }
    
    @Override
    public String getUniqueId() {
        
        return UIDGenerator.generate(Account.TYPE, getSession());
    }
}
