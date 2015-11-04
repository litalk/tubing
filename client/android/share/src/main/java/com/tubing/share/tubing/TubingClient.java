package com.tubing.share.tubing;

import com.tubing.share.restclient.*;

/**
 * Created by kornfeld on 04/11/2015.
 */
public class TubingClient {

    RestClient<RestRequest,RestClientResponse> _restClient = new RestClientImpl(30000);

    public void add(String query) {

        final RestClientResponse post = _restClient.post(new TubingAddRequest("http://localhost:8080", query));
        post.getStatusInfo();
    }
}
