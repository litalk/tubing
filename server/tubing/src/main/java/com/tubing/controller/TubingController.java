package com.tubing.controller;

import com.tubing.common.ObjectMapperUtils;
import com.tubing.dal.model.Account;
import com.tubing.service.TubingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tubing")
public class TubingController {

    @Autowired
    TubingService _service;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(@RequestBody String account) {

        System.out.println(ObjectMapperUtils.to(account, Account.class));
    }

    @RequestMapping(value = "{query}", method = RequestMethod.GET)
    public void add(@PathVariable("query") String query) {

        _service.process(query);
    }
}
