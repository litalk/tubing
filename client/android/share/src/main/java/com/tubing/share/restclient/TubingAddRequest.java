package com.tubing.share.restclient;

import javax.ws.rs.core.MediaType;

/**
 * Created by kornfeld on 04/11/2015.
 */
public class TubingAddRequest extends RestRequest {

    public TubingAddRequest(String uri, String query) {

        super(String.format("%s/tubing/%s", uri, query), MediaType.APPLICATION_JSON);
    }
}
