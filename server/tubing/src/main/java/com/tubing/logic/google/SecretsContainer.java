package com.tubing.logic.google;

import java.io.FileReader;
import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson2.JacksonFactory;

public class SecretsContainer {

    private static GoogleClientSecrets _secrets;

    public synchronized static GoogleClientSecrets get() {

        if (_secrets == null) {
            try {
                _secrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader("client_secret_windy.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return _secrets;
    }

    public static String getJwt() {

        return get().getDetails().getClientSecret();
    }
}
