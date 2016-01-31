/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.tubing.logic.google;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.tubing.common.TubingException;

import java.io.IOException;
import java.util.List;

public class YouTubeSearch extends YouTubeAPI {

    private enum Type {

        Video("video"), PlayList("playlist");

        private final String _type;

        Type(String type) {

            _type = type;
        }

        @Override
        public String toString() {

            return _type;
        }
    }

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

    public YouTubeSearch(YouTube youTube) {

        super(youTube);
    }

    public String searchVideo(String query) {

        return search(query, Type.Video);
    }

    public String searchPlayList(String query) {

        return search(query, Type.PlayList);
    }

    private String search(String query, Type type) {

        String id = null;
        try {
            List<SearchResult> searchResults = getSearchService(query, type).execute().getItems();
            if (searchResults.size() > 0) {
                SearchResult searchResult = searchResults.get(0);
                print(query, searchResult);
                id = searchResult.getId().getVideoId();
            } else {
                System.out.println(String.format("No items found for query: %s", query));
            }
        } catch (Exception e) {
            throw new TubingException(String.format("Failed to search using YouTube, query: %s", query), e);
        }

        return id;
    }

    private YouTube.Search.List getSearchService(String query, Type type) throws IOException {

        // Define the API request for retrieving search results.
        YouTube.Search.List ret = getYouTube().search().list("id,snippet");
        ret.setQ(query);
        // Restrict the search results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type
        // Acceptable values are: channel, playlist, video
        ret.setType(type.toString());
        // To increase efficiency, only retrieve the fields that the
        // application uses.
        //search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        ret.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

        return ret;
    }

    private void print(String query, SearchResult searchResult) {

        System.out.println(String.format(
                "Query: %s, Found Video: Id: %s, Title: %s, URL: %s",
                query,
                searchResult.getId().getVideoId(),
                searchResult.getSnippet().getTitle(),
                searchResult.getSnippet().getThumbnails().getDefault().getUrl()));
    }
}

