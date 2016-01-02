package com.tubing.logic.google;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

public class YouTubeBuilder {

    private static final JsonFactory _jsonFactory = JacksonFactory.getDefaultInstance();
    private static HttpTransport _transport;

    public static YouTube build(String accessToken) {

        return new YouTube.Builder(
                getTransport(),
                _jsonFactory,
                getCredential(accessToken)).
                setApplicationName("Tubing").
                build();
    }

    public static Credential getCredential(String accessToken) {

        try {
            return new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static HttpTransport getTransport() {

        if(_transport == null) {
            try {
                _transport = GoogleNetHttpTransport.newTrustedTransport();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return _transport;
    }
}
