package com.tubing.common;

import java.util.Map;

public class StringUtils {
    
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String PERIOD = ".";
    public static final String TAB = "\t";
    
    public static boolean isNullOrEmpty(String value) {
        
        return (value == null) || (value.length() == 0);
    }
    
    public static <TKey, TValue> String asString(Map<TKey, TValue> params) {
        
        StringBuilder ret = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<TKey, TValue> currParam : params.entrySet()) {
                ret.append(String.format("%s = %s, ", currParam.getKey(), currParam.getValue()));
            }
            ret.delete(ret.length() - 2, ret.length());
        }
        
        return ret.toString();
    }
    
    public static String toCamelCase(String s, String separator) {
        
        StringBuilder ret = new StringBuilder();
        String[] parts = s.split(separator);
        for (String part : parts) {
            if (!StringUtils.isNullOrEmpty(part)) {
                ret.append(toProperCase(part));
            }
        }
        
        return ret.toString();
    }
    
    private static String toProperCase(String s) {
        
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
