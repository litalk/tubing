package com.tubing.controller;

import com.google.api.services.youtube.YouTube;
import com.tubing.common.TubingException;
import com.tubing.dal.EntityFetcher;
import com.tubing.dal.model.Session;
import com.tubing.logic.UIDGenerator;
import com.tubing.logic.google.YouTubeBuilder;
import com.tubing.logic.google.YouTubePlaylist;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.logic.processor.QueryProcessor;
import com.tubing.logic.processor.QueryProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/tubing/api")
public class TubingController {
    
    @Autowired
    private YouTubeBuilder _builder;
    @Autowired
    private EntityFetcher _fetcher;

    @RequestMapping(value = "health", method = RequestMethod.GET)
    public void health() {}

    @RequestMapping(value = "playlist/{playlist-id}/items", method = RequestMethod.POST)
    public void playlist(HttpServletRequest request, @PathVariable("playlist-id") String playlistId, @RequestBody String query)
            throws UnsupportedEncodingException {
            
        YouTube youTube = _builder.build(getUserId(request));
        final String youtubeQuery = extractYoutubeQuery(query);
        new YouTubePlaylist(youTube).update(playlistId, new YouTubeSearch(youTube).searchVideo(youtubeQuery));
    }
    
    private String extractYoutubeQuery(String query) {
        
        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);
        
        return queryProcessor.process(query);
    }

    private String getUserId(HttpServletRequest request) {

        String key = UIDGenerator.generate(Session.TYPE, (String) request.getAttribute("session-id"));
        Session session = _fetcher.get(key, Session.class);
        if (session == null) {
            throw new TubingException("Session not found, please login again.");
        }

        return session.getUserId();
    }
}
