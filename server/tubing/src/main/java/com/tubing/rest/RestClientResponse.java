package com.tubing.rest;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.StatusType;
import java.io.InputStream;
import java.util.Map;

public interface RestClientResponse<TEntity> {

    TEntity getEntity();

    InputStream getEntityStream();
    
    StatusType getStatusInfo();

    Map<String, NewCookie> getCookies();
}
