package com.tubing.share.restclient;

public class RestClientException extends RuntimeException {
    
    private static final long serialVersionUID = -3884419033773907260L;
    
    private final int _statusCode;
    private String _reason;
    
    public RestClientException(String message, int statusCode, String reason) {
        
        super(message);
        _statusCode = statusCode;
        _reason = reason;
    }
    
    public int getStatusCode() {
        
        return _statusCode;
    }

    public String getReason() {

        return _reason;
    }
}
