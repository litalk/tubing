package com.tubing.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import com.tubing.dal.EntityFetcher;
import com.tubing.dal.model.Session;
import com.tubing.logic.UIDGenerator;

public class JwtFilter extends GenericFilterBean {
    
    private EntityFetcher _fetcher;

    public JwtFilter(EntityFetcher fetcher) {
        
        _fetcher = fetcher;
    }
    
    @Override
    public void doFilter(
            final ServletRequest req,
            final ServletResponse res,
            final FilterChain chain) throws IOException, ServletException {
            
        try {
            final HttpServletRequest request = (HttpServletRequest) req;
            request.setAttribute("user-id", getUserId(JwtHelper.getToken(request)));
        } catch (final Exception ex) {
            throw new ServletException("Invalid token.");
        }
        
        chain.doFilter(req, res);
    }

    private String getUserId(String token) {
        
        String key = UIDGenerator.generate(Session.TYPE, JwtHelper.getSession(token));
        Session session = _fetcher.get(key, Session.class);
        if (session == null) {
            throw new RuntimeException("Session not found in DB (should login again).");
        }
        
        return session.getUserId();
    }
}