package com.tubing.logic.google;

import com.google.api.services.youtube.YouTube;

public class YouTubeService {

    private static YouTube _youtube;

    public synchronized static YouTube getInstance() {

        if(_youtube == null) {
            initialize();
        }

        return _youtube;
    }

    private static void initialize() {

        _youtube = new YouTube.Builder(
                GoogleCredentialContainer.HTTP_TRANSPORT,
                GoogleCredentialContainer.JSON_FACTORY,
                GoogleCredentialContainer.getCredential()).
                setApplicationName("Tubing").
                build();
    }
}
