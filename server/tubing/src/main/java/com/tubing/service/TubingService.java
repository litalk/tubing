package com.tubing.service;

import com.tubing.google.YouTubePlaylist;
import com.tubing.google.YouTubeSearch;
import org.springframework.stereotype.Service;

@Service
public class TubingService {

    public static void addToPlaylist(String query) {

        new YouTubePlaylist().update(new YouTubeSearch().search(query));
    }
}
