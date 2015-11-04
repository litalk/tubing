package com.tubing.share.restclient;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class RestClientImpl implements RestClient<RestRequest, RestClientResponse> {

    private final int READ_TIMEOUT;
    private Client _client;

    public RestClientImpl(int readTimeout) {

        READ_TIMEOUT = readTimeout;
        _client = ClientBuilder.newBuilder()
                .register(JacksonFeature.class)
                .property(ClientProperties.CONNECT_TIMEOUT, 30000)
                .property(ClientProperties.READ_TIMEOUT, READ_TIMEOUT)
                .build();
    }

    @Override
    public RestClientResponse post(RestRequest request) {

        Builder builder = prepareRequest(request);
        Entity<Object> entity = Entity.entity(request.getEntity(), request.getRequestMediaType());
        Response response = builder.accept(request.getResponseMediaType()).post(entity);
        checkResponse(response);

        return new RestClientResponseImpl(response);
    }

    @Override
    public RestClientResponse get(RestRequest request) {

        Builder builder = prepareRequest(request);
        Response response = builder.accept(request.getResponseMediaType()).get();
        checkResponse(response);

        return new RestClientResponseImpl(response);
    }

    @Override
    public RestClientResponse put(RestRequest request) {

        WebTarget webResource = _client.target(request.getUri());
        Entity<Object> entity = Entity.entity(request.getEntity(), request.getRequestMediaType());
        Response response =
                webResource.request().accept(request.getResponseMediaType()).put(entity);
        checkResponse(response);

        return new RestClientResponseImpl(response);
    }

    private void checkResponse(javax.ws.rs.core.Response response) throws RestClientException {

        Response.StatusType statusInfo = response.getStatusInfo();
        if (statusInfo.getFamily() != Family.SUCCESSFUL) {
            String responseStr = "";
            try (Scanner scanner =
                         new Scanner((InputStream) response.getEntity(), StandardCharsets.UTF_8.toString())) {
                responseStr = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            }
            throw new RestClientException(String.format(
                        "Failed : HTTP error code: %d, response message: %s",
                        statusInfo.getStatusCode(),
                        responseStr.isEmpty() ? statusInfo.getReasonPhrase() : responseStr),
                    statusInfo.getStatusCode(),
                    responseStr);
        }
    }

    private Builder prepareRequest(RestRequest request) {

        WebTarget webTarget = _client.target(request.getUri());
        Map<String, String[]> queryParams = request.getQueryParams();
        if (queryParams != null) {
            for (Entry<String, String[]> param : queryParams.entrySet()) {
                webTarget = webTarget.queryParam(param.getKey(), param.getValue());
            }
        }
        Builder builder = webTarget.request();
        for (NewCookie currCookie : request.getCookies()) {
            builder = builder.cookie(currCookie.toCookie());
        }
        for (Entry<String, Object> currEntry : request.getHeaders().entrySet()) {
            builder = builder.header(currEntry.getKey(), currEntry.getValue());
        }

        return builder;
    }
}
