package com.tubing.controller;

import com.google.api.services.youtube.YouTube;
import com.tubing.logic.google.YouTubeBuilder;
import com.tubing.logic.google.YouTubePlayList;
import com.tubing.logic.google.YouTubeSearch;
import com.tubing.logic.AccountLogic;
import com.tubing.common.ObjectMapperUtils;
import com.tubing.dal.ElasticService;
import com.tubing.dal.model.Account;
import com.tubing.service.QueryProcessor.QueryProcessor;
import com.tubing.service.QueryProcessor.QueryProcessorFactory;
import com.tubing.service.TubingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping("/tubing")
public class TubingController {

    @Autowired
    private TubingService _service;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody String jsonAccount) {

        HttpStatus ret = HttpStatus.OK;
        Account account = ObjectMapperUtils.to(jsonAccount, Account.class);
        Account search = ElasticService.search(Account.class, Account.TYPE,
                AccountLogic.getSearchByEmailQuery(account));
        if(search == null) {
            ret = HttpStatus.valueOf(ElasticService.insert(account).getStatusInfo().getStatusCode());
        }

        return new ResponseEntity<>(ret);
    }

    @RequestMapping(value = "playlist", method = RequestMethod.POST)
    public void addToPlayList(@RequestBody String accessToken) throws UnsupportedEncodingException {

        final String youtubeQuery = extractYoutubeQuery("I just used Shazam to discover Llévame Contigo by Romeo Santos. http://shz.am/t54018231");
        YouTube youTube = YouTubeBuilder.build(URLDecoder.decode(accessToken, "UTF-8"));
        new YouTubePlayList(youTube).update(new YouTubeSearch(youTube).search(youtubeQuery));
    }

    private String extractYoutubeQuery(String query) {

        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);

        return queryProcessor.process(query);
    }
}
