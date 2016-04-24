package com.tubing.controller;

import com.google.api.services.youtube.YouTube;
import com.tubing.logic.google.YouTubeBuilder;
import com.tubing.logic.google.YouTubePlaylist;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.logic.processor.QueryProcessor;
import com.tubing.logic.processor.QueryProcessorFactory;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping("/tubing")
public class TubingController {
    
    @RequestMapping(value = "playlist", method = RequestMethod.POST)
    public void addToPlayList(@CookieValue("tubing_user-id") String userId, @CookieValue("tubing_auth-code") String authCode, @RequestBody String query) throws UnsupportedEncodingException {

        final String youtubeQuery =
                extractYoutubeQuery(query);
        YouTube youTube = YouTubeBuilder.build(userId, URLDecoder.decode(authCode, "UTF-8"));
        new YouTubePlaylist(youTube).update(new YouTubeSearch(youTube).searchVideo(youtubeQuery));
    }
    
    private String extractYoutubeQuery(String query) {
        
        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);
        
        return queryProcessor.process(query);
    }
}
