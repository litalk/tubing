package com.tubing.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.youtube.YouTube;
import com.tubing.logic.google.YouTubeBuilder;
import com.tubing.logic.google.YouTubePlayList;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.service.QueryProcessor;
import com.tubing.service.QueryProcessorFactory;

@RestController
@RequestMapping("/tubing")
public class TubingController {
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody String authCode) {
        
        HttpStatus ret = HttpStatus.OK;
        
        return new ResponseEntity<>(ret);
    }
    
    @RequestMapping(value = "playlist", method = RequestMethod.POST)
    public void addToPlayList(@RequestBody String authCode) throws UnsupportedEncodingException {
        
        final String youtubeQuery =
                extractYoutubeQuery("I just used Shazam to discover Llevame Contigo by Romeo Santos. http://shz.am/t54018231");
        YouTube youTube = YouTubeBuilder.build(URLDecoder.decode(authCode, "UTF-8"));
        new YouTubePlayList(youTube).update(new YouTubeSearch(youTube).searchVideo(youtubeQuery));
    }
    
    private String extractYoutubeQuery(String query) {
        
        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);
        
        return queryProcessor.process(query);
    }
}
