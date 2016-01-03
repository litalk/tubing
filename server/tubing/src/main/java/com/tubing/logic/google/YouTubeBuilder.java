package com.tubing.logic.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

public class YouTubeBuilder {

//    private static HttpTransport _transport;

    public static YouTube build(String accessToken) {

        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        return new YouTube.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential).
                setApplicationName("Tubing").
                build();
    }

//    public static Credential getCredential(String accessToken) {
//
//        try {
//            return new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public synchronized static HttpTransport getTransport() {
//
//        if(_transport == null) {
//            try {
//                _transport = GoogleNetHttpTransport.newTrustedTransport();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return _transport;
//    }
}
