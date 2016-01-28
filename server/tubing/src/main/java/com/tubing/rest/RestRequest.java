package com.tubing.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestRequest {
    
    private String _uri;
    private Map<String, String[]> _queryParams = new HashMap<>();
    private Object _entity;
    private String _requestMediaType;
    private String _responseMediaType;
    private Map<String, NewCookie> _cookies = new HashMap<>();
    private Map<String, Object> _headers = new HashMap<>();

    public RestRequest(String uri) {

        this(uri, null);
    }
    
    public RestRequest(String uri, Object entity) {
        
        this(uri, entity, MediaType.APPLICATION_JSON);
    }

    public RestRequest(String uri, Object entity, String requestContentType) {

        _uri = uri;
        _entity = entity;
        _requestMediaType = requestContentType;
        _responseMediaType = MediaType.APPLICATION_JSON;
    }

    public String getUri() {
        
        return _uri;
    }
    
    public Object getEntity() {
        
        return _entity;
    }
    
    public void setEntity(Object entity) {
        
        _entity = entity;
    }
    
    public String getRequestMediaType() {
        
        return _requestMediaType;
    }
    
    public String getResponseMediaType() {
        
        return _responseMediaType;
    }
    
    public Map<String, String[]> getQueryParams() {
        
        return _queryParams;
    }
    
    public void queryParam(String query, String... value) {
        
        _queryParams.put(query, value);
    }
    
    public Collection<NewCookie> getCookies() {
        
        return _cookies.values();
    }
    
    public void putCookie(NewCookie cookie) {
        
        _cookies.put(cookie.getName(), cookie);
    }
    
    public Map<String, Object> getHeaders() {
        
        return _headers;
    }
    
    public void addHeader(String name, String value) {
        
        _headers.put(name, value);
    }
    
    @Override
    public String toString() {
        
        return String.format(
                "URI: %s, QueryParams: %s, RequestMediaType: %s, ResponseMediaType: %s, Cookies: %s, Headers: %s",
                _uri,
                _queryParams.toString(),
                _requestMediaType,
                _responseMediaType,
                asString(_cookies),
                _headers.toString());
    }
    
    private String asString(Map<String, NewCookie> cookies) {
        
        StringBuilder ret = new StringBuilder();
        if (cookies != null && cookies.size() > 0) {
            ret.append("[");
            for (NewCookie currCookie : cookies.values()) {
                ret.append(String.format(
                        "name = %s, value = %s, ",
                        currCookie.getName(),
                        currCookie.getValue()));
            }
            ret.delete(ret.length() - 2, ret.length());
            ret.append("]");
        }
        
        return ret.toString();
    }
}
