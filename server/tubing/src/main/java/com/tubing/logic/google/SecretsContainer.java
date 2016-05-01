package com.tubing.logic.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.tubing.common.TubingException;

import java.io.FileReader;
import java.io.IOException;

public class SecretsContainer {

    public static final String SECRETS_FILE = "src/main/resources/client_secret_windy.json";
    private static GoogleClientSecrets _secrets;

    public synchronized static GoogleClientSecrets get() {

        if (_secrets == null) {
            try {
                _secrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
                        new FileReader(SECRETS_FILE));
            } catch (IOException e) {
                throw new TubingException("Failed loading GoogleClientSecrets", e);
            }
        }

        return _secrets;
    }

    public static String getJwt() {

        return get().getDetails().getClientSecret();
    }
}
