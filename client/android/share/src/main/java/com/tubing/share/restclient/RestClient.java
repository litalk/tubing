package com.tubing.share.restclient;

public interface RestClient<TRequest extends RestRequest, TResponse extends RestClientResponse> {
    
    TResponse put(TRequest request);
    
    TResponse post(TRequest request);
    
    TResponse get(TRequest request);
}
