package com.tubing.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.mortbay.jetty.HttpHeaders;
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
            request.setAttribute("user-id", getUserId(getToken(request)));
        } catch (final Exception ex) {
            throw new ServletException("Invalid token.");
        }
        
        chain.doFilter(req, res);
    }
    
    private String getToken(HttpServletRequest request) throws ServletException {
        
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }
        
        return authHeader.substring("Bearer ".length());
    }

    private String getUserId(String token) throws ServletException {
        
        String key = UIDGenerator.generate(Session.TYPE, JwtHelper.getSession(token));
        Session session = _fetcher.get(key, Session.class);
        if (session == null) {
            throw new ServletException("Invalid token.");
        }
        
        return session.getUserId();
    }
}