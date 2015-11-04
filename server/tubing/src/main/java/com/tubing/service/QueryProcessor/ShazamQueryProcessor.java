package com.tubing.service.QueryProcessor;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by kornfeld on 04/11/2015.
 */
public class ShazamQueryProcessor implements QueryProcessor {

    private static final String TAG_PREFIX = "I just used Shazam to discover";
    private static final String TAG_SUFFIX = "http://";

    @Override
    public String process(String query) {

        return StringUtils.substringBetween(query, TAG_PREFIX, TAG_SUFFIX);
    }
}
