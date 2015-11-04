package com.tubing.controller;

import com.tubing.service.TubingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tubing")
public class TubingController {

    @Autowired
    TubingService _service;

    @RequestMapping("{query}")
    public void add(@PathVariable("query") String query) {

        _service.addToPlaylist(query);
    }
}
