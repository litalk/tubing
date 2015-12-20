package com.tubing.client.android.common;

import java.nio.charset.StandardCharsets;

/**
 * Created by barshean on 12/20/2015.
 */
public class StringUtils {

    public static byte[] toByteArray(String str) {

        return str.getBytes(StandardCharsets.UTF_8);
    }
}
