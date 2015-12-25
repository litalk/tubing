package com.tubing.service;

import com.tubing.logic.google.YouTubePlaylist;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.service.QueryProcessor.QueryProcessor;
import com.tubing.service.QueryProcessor.QueryProcessorFactory;
import org.springframework.stereotype.Service;

@Service
public class TubingService {

    public void process(String query) {

        final String youtubeQuery = extractYoutubeQuery(query);
        new YouTubePlaylist().update(new YouTubeSearch().search(youtubeQuery));
    }

    private String extractYoutubeQuery(String query) {

        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);

        return queryProcessor.process(query);
    }
}
