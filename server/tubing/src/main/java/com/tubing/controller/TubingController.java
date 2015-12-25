package com.tubing.controller;

import com.tubing.logic.google.YouTubePlaylist;
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
    public void add(@RequestBody String query) {

        final String youtubeQuery = extractYoutubeQuery(query);
        new YouTubePlaylist().update(new YouTubeSearch().search(youtubeQuery));
    }

    private String extractYoutubeQuery(String query) {

        final QueryProcessor queryProcessor = QueryProcessorFactory.get(query);

        return queryProcessor.process(query);
    }
}
