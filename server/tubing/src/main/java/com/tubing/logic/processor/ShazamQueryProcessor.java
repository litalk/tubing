package com.tubing.logic.processor;

import org.apache.commons.lang3.StringUtils;

public class ShazamQueryProcessor implements QueryProcessor {
    
    @Override
    public String process(String query) {
        
        String ret = StringUtils.substringBetween(query, "I just used Shazam to discover", "http");
        if (ret == null) {
            ret =
                    StringUtils.substringBetween(
                            query,
                            "Found ",
                            " with Shazam, have a listen: http");
        }
        
        return ret;
    }
}
