package com.tubing.logic.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GoogleCredentialContainer {

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE);
    private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credential/google");
    private static final FileDataStoreFactory DATA_STORE_FACTORY;
    private static Credential _credential;

    static {

        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Credential authorize() {

        Credential ret = null;
        try {
            // Load client secrets.
            final String JSON = "/client_secret_windy.json";
            InputStream in =
                    GoogleCredentialContainer.class.getResourceAsStream(JSON);
            if (in == null) {
                throw new RuntimeException("Failed to find client secret json");
            }
            System.out.println("Loading secret key, file: " + JSON);
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                            .setDataStoreFactory(DATA_STORE_FACTORY)
                            .setAccessType("offline")
                            .build();
            ret = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");
            System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Credential getCredential() {

        if (_credential == null) {
            _credential = authorize();
        }

        return _credential;
    }
}
