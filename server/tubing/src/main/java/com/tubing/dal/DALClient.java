package com.tubing.dal;

public interface DALClient {
    
    void set(String key, String value);
    
    String get(String key);
}
