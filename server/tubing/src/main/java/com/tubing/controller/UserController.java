package com.tubing.controller;

import java.util.UUID;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tubing.dal.EntityPersister;
import com.tubing.dal.model.Account;
import com.tubing.dal.model.Session;
import com.tubing.logic.UserLogic;

@RestController
@RequestMapping("/tubing")
public class UserController {
    
    @Autowired
    private UserLogic _logic;
    @Autowired
    private EntityPersister _persister;
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final String authCode) throws ServletException {
        
        Account account = null;
        try {
            account = _logic.login(authCode);
        } catch (Exception ex) {
            account = null;
        }
        if (account == null) {
            throw new ServletException("Invalid login");
        }
        String sessionKey = UUID.randomUUID().toString();
        _persister.insert(new Session(sessionKey, account.getUserId()));
        
        return new LoginResponse(JwtHelper.build(account.getName(), sessionKey));
    }
    
    @SuppressWarnings("unused")
    private static class LoginResponse {
        
        public String token;
        
        public LoginResponse(final String token) {
            
            this.token = token;
        }
    }
}