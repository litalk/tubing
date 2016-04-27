package com.tubing.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {
    
    @Override
    public void doFilter(
            final ServletRequest req,
            final ServletResponse res,
            final FilterChain chain) throws IOException, ServletException {
            
        try {
            final HttpServletRequest request = (HttpServletRequest) req;
            request.setAttribute("session-id", JwtHelper.getSession(JwtHelper.getToken(request)));
        } catch (final Exception ex) {
            throw new ServletException("Invalid token.");
        }
        
        chain.doFilter(req, res);
    }
}