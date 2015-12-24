package com.tubing.rest;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.StatusType;
import java.io.InputStream;
import java.util.Map;

public class RestClientResponseImpl<TEntity> implements RestClientResponse {
    
    private final TEntity _entity;
    private final InputStream _entityStream;
    private final Map<String, NewCookie> _cookies;
    private final StatusType _status;

    public RestClientResponseImpl(TEntity entity, Map<String, NewCookie> cookies, StatusType status) {

        _entity = entity;
        _cookies = cookies;
        _status = status;
        _entityStream = null;
    }

    public RestClientResponseImpl(InputStream entityStream, Map<String, NewCookie> cookies, StatusType status) {

        _entity = null;
        _cookies = cookies;
        _status = status;
        _entityStream = entityStream;
    }

    /**
     *
     * @return TEntity, can be null.
     */
    @Override
    public TEntity getEntity() {

        return _entity;
    }

    /**
     *
     * @return entity as stream, can be null.
     */
    @Override
    public InputStream getEntityStream() {

        return _entityStream;
    }

    @Override
    public StatusType getStatusInfo() {
        
        return _status;
    }
    
    @Override
    public Map<String, NewCookie> getCookies() {
        
        return _cookies;
    }
}
