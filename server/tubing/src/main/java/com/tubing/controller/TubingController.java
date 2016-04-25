package com.tubing.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.youtube.YouTube;
import com.tubing.logic.google.YouTubeBuilder;
import com.tubing.logic.google.YouTubePlaylist;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.logic.processor.QueryProcessor;
import com.tubing.logic.processor.QueryProcessorFactory;

@RestController
@RequestMapping("/tubing/api")
public class TubingController {

    @Autowired
    private YouTubeBuilder _builder;

    @RequestMapping(value = "playlist", method = RequestMethod.POST)
    public void playlist(HttpServletRequest request, @RequestBody String query) throws UnsupportedEncodingException {

        String userId = (String) request.getAttribute("user-id");
        YouTube youTube = _builder.build(userId);
        final String youtubeQuery = extractYoutubeQuery(query);
        new YouTubePlaylist(youTube).update(new YouTubeSearch(youTube).searchVideo(youtubeQuery));
    }
    
    private String extractYoutubeQuery(String query) {
        
        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);
        
        return queryProcessor.process(query);
    }
}
