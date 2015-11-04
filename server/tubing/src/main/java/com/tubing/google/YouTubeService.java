package com.tubing.google;

import com.google.api.services.youtube.YouTube;

public class YouTubeService {

    private static YouTube _youtube;

    static {

        _youtube = new YouTube.Builder(
                GoogleCredentialContainer.HTTP_TRANSPORT,
                GoogleCredentialContainer.JSON_FACTORY,
                GoogleCredentialContainer.getCredential()).
                setApplicationName("Tubing").
                build();
    }

    public static YouTube getInstance() {

        return _youtube;
    }
}
