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
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;

public class YouTubeSearch {

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    public String search(String query) {

        SearchResult ret = null;
        try {
            SearchListResponse searchResponse = getSearchService(query).execute();
            ret = searchResponse.getItems().get(0);
            print(query, ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret.getId().getVideoId();
    }

    private YouTube.Search.List getSearchService(String query) throws IOException {

        // Define the API request for retrieving search results.
        YouTube.Search.List ret = YouTubeService.getInstance().search().list("id,snippet");
        ret.setQ(query);
        // Restrict the search results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type
        // Acceptable values are: channel, playlist, video
        ret.setType("video");
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

