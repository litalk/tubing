package com.tubing.logic.google;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.tubing.TubingApplication;
import com.tubing.dal.EntityFetcher;
import com.tubing.dal.model.Account;
import com.tubing.logic.UIDGenerator;

@Component
public class YouTubeBuilder {

    @Autowired
    private EntityFetcher _fetcher;

    public YouTube build(String userId) throws IOException {

        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Account account = _fetcher.get(UIDGenerator.generate(Account.TYPE, userId), Account.class);
        GoogleCredential credential = new GoogleCredential.Builder().
                setTransport(transport).
                setJsonFactory(jsonFactory).
                setClientSecrets(SecretsContainer.get()).
                build().
                setRefreshToken(account.getRefreshToken()).
                setAccessToken(account.getAccessToken());

        return new YouTube.Builder(transport, jsonFactory, credential).
                setApplicationName(TubingApplication.NAME).
                build();
    }
}
