package com.tubing.logic;

public class UIDGenerator {

    public static String generate(String type, String key) {

        return String.format("%s:%s", type, key);
    }
}
