package com.tubing.dal;

public interface DALClient {

    void add(String key, String value);

    String get(String key);
}
