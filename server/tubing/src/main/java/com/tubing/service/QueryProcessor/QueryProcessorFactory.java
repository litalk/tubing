package com.tubing.service.QueryProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kornfeld on 04/11/2015.
 */
public class QueryProcessorFactory {

    private static Map<String, QueryProcessor> _keywordToQueryProcessor = new HashMap<>();
    private static final String DEFAULT = "default";

    static {

        _keywordToQueryProcessor.put("Shazam", new ShazamQueryProcessor());
        _keywordToQueryProcessor.put(DEFAULT, new DefaultQueryProcessor());
    }

    public static QueryProcessor get(String query) {

        final Optional<String> keyword = _keywordToQueryProcessor.keySet().stream().
                filter(s -> query.contains(s)).
                findAny();

        return keyword.isPresent() ? _keywordToQueryProcessor.get(keyword.get()) : _keywordToQueryProcessor.get(DEFAULT);
    }
}
