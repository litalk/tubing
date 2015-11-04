package com.tubing.share.restclient;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.StatusType;
import java.io.InputStream;
import java.util.Map;

public interface RestClientResponse {
    
    <T> T getEntity(GenericType<T> entityType);
    
    <T> T getEntity(Class<T> entityType);
    
    StatusType getStatusInfo();
    
    InputStream getEntityStream();
    
    Map<String, NewCookie> getCookies();
}
