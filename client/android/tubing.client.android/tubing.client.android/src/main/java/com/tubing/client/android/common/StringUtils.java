package com.tubing.client.android.common;

import java.nio.charset.StandardCharsets;

public class StringUtils {

    public static byte[] toByteArray(String str) {

        return str.getBytes(StandardCharsets.UTF_8);
    }
}
