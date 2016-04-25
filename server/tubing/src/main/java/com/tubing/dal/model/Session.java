package com.tubing.dal.model;

import com.tubing.logic.UIDGenerator;

public class Session implements Entity {

    public static final String TYPE = "session";

    private String _session;
    private String _userId;

    public Session(String session, String userId) {

        _session = session;
        _userId = userId;
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
