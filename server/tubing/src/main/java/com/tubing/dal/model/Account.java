package com.tubing.dal.model;

public class Account implements ElasticEntity {

    public static final String TYPE = "account";
    public static final String EMAIL_FIELD = "_email";

    private String _email;
    private String _username;
    private String _token;

    private Account() {}

    public Account(String email, String username, String token) {

        this();
        _email = email;
        _username = username;
        _token = token;
    }

    public String getEmail() {

        return _email;
    }

    public String getUsername() {

        return _username;
    }

    public String getToken() {

        return _token;
    }

    @Override
    public String toString() {

        return String.format("email: %s, username: %s, token: %s", _email, _username, _token);
    }

    @Override
    public String getType() {

        return TYPE;
    }
}
