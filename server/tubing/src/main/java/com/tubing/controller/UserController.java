package com.tubing.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tubing.dal.model.Account;
import com.tubing.logic.UserLogic;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/tubing")
public class UserController {

    @Autowired
    private UserLogic _logic;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final String authCode)
            throws ServletException {

        Account account = null;
        try {
            account = _logic.login(authCode);
        } catch (Exception ex) {
            account = null;
        }
        if (account == null) {
            throw new ServletException("Invalid login");
        }

        return new LoginResponse(Jwts.builder().setSubject(account.getEmail())
                .claim("roles", "na").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {

        public String token;

        public LoginResponse(final String token) {

            this.token = token;
        }
    }
}