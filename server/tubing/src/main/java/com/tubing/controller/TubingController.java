package com.tubing.controller;

import com.tubing.service.TubingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tubing")
public class TubingController {

    @Autowired
    TubingService _service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(){//@RequestBody User user) {

        System.out.println("hello");
        //_service.process(query);
    }

    @RequestMapping(value = "{query}", method = RequestMethod.POST)
    public void add(@PathVariable("query") String query) {

        _service.process(query);
    }
}
