package com.tubing.share.restclient;

import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class RestClientResponseImpl implements RestClientResponse {
    
    private final Response _response;
    
    public RestClientResponseImpl(Response response) {
        
        _response = response;
    }
    
    @Override
    public <T> T getEntity(Class<T> entityType) {
        
        return _response.readEntity(entityType);
    }
    
    @Override
    public <T> T getEntity(GenericType<T> entityType) {
        
        return _response.readEntity(entityType);
    }
    
    @Override
    public StatusType getStatusInfo() {
        
        return _response.getStatusInfo();
    }
    
    @Override
    public InputStream getEntityStream() {
        
        return _response.hasEntity() ? (InputStream) _response.getEntity() : null;
    }
    
    @Override
    public Map<String, NewCookie> getCookies() {
        
        return _response.getCookies();
    }
}
