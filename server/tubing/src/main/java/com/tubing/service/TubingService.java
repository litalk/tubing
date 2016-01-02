package com.tubing.service;

import com.google.api.services.youtube.YouTube;
import com.tubing.logic.google.YouTubeBuilder;
import com.tubing.logic.google.YouTubePlayList;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.service.QueryProcessor.QueryProcessor;
import com.tubing.service.QueryProcessor.QueryProcessorFactory;
import org.springframework.stereotype.Service;

@Service
public class TubingService {

    public void process(String accessToken, String query) {

        final String youtubeQuery = extractYoutubeQuery(query);
        YouTube youTube = YouTubeBuilder.build(accessToken);
        new YouTubePlayList(youTube).update(new YouTubeSearch(youTube).search(youtubeQuery));
    }

    private String extractYoutubeQuery(String query) {

        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);

        return queryProcessor.process(query);
    }
}
