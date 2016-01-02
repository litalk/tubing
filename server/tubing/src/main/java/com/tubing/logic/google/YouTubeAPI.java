package com.tubing.logic.google;

import com.google.api.services.youtube.YouTube;

public class YouTubeAPI {

    private final YouTube _youTube;

    public YouTubeAPI(YouTube youTube) {

        _youTube = youTube;
    }

    protected YouTube getYouTube() {

        return _youTube;
    }
}
