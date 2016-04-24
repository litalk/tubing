package com.tubing.dal;

import java.util.Map;

public interface DALClient {
    
    void set(String key, String value);
    
    String get(String key);

    void hmset(String key, Map<String, String> fieldToValues);

    Map<String, String> hgetAll(String key);
}
