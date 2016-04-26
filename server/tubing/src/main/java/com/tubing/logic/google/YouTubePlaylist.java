package com.tubing.logic.google;

import java.io.IOException;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.tubing.common.StringUtils;

public class YouTubePlaylist extends YouTubeAPI {
    
    private static final String PLAYLIST_TITLE = "Tubing";
    
    public YouTubePlaylist(YouTube youTube) {
        
        super(youTube);
    }
    
    public void update(String playlistId, String videoId) {

        try {
            insertPlaylistItem(getPlaylist(playlistId), videoId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getPlaylist(String playlistId) throws IOException {

        return StringUtils.isNullOrEmpty(playlistId) || "tubing".equals(playlistId) ?
                createPlaylist(PLAYLIST_TITLE) : playlistId;
    }
    
    /**
     * This code constructs the playlist resource that is being inserted. It defines the playlist's
     * title, description, and privacy status.
     */
    private String createPlaylist(String title) throws IOException {
        
        PlaylistSnippet playlistSnippet = new PlaylistSnippet();
        playlistSnippet.setTitle(title);
        playlistSnippet.setDescription("A private playlist created by Tubing App");
        PlaylistStatus playlistStatus = new PlaylistStatus();
        playlistStatus.setPrivacyStatus("private");
        
        Playlist youTubePlaylist = new Playlist();
        youTubePlaylist.setSnippet(playlistSnippet);
        youTubePlaylist.setStatus(playlistStatus);
        
        // Call the API to insert the new playlist. In the API call, the first
        // argument identifies the resource parts that the API response should
        // contain, and the second argument is the playlist being inserted.
        YouTube.Playlists.Insert playlistInsertCommand =
                getYouTube().playlists().insert("snippet,status", youTubePlaylist);
        Playlist playlistInserted = playlistInsertCommand.execute();
        
        // Print data from the API response and return the new playlist's
        // unique playlist ID.
        System.out.println("New Playlist name: " + playlistInserted.getSnippet().getTitle());
        System.out.println(" - Privacy: " + playlistInserted.getStatus().getPrivacyStatus());
        System.out.println(" - Description: " + playlistInserted.getSnippet().getDescription());
        System.out.println(" - Posted: " + playlistInserted.getSnippet().getPublishedAt());
        System.out.println(" - Channel: " + playlistInserted.getSnippet().getChannelId() + "\n");
        
        return playlistInserted.getId();
    }
    
    private String insertPlaylistItem(String playlistId, String videoId) throws IOException {
        
        // Define a resourceId that identifies the video being added to the
        // playlist.
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);
        
        // Set fields included in the playlistItem resource's "snippet" part.
        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setTitle("effi this is the title");
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);
        
        // Create the playlistItem resource and set its snippet to the
        // object created above.
        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(playlistItemSnippet);
        
        // Call the API to add the playlist item to the specified playlist.
        // In the API call, the first argument identifies the resource parts
        // that the API response should contain, and the second argument is
        // the playlist item being inserted.
        YouTube.PlaylistItems.Insert playlistItemsInsertCommand =
                getYouTube().playlistItems().insert("snippet,contentDetails", playlistItem);
        PlaylistItem returnedPlaylistItem = playlistItemsInsertCommand.execute();
        
        // Print data from the API response and return the new playlist
        // item's unique playlistItem ID.
        
        System.out.println(
                "New PlaylistItem name: " + returnedPlaylistItem.getSnippet().getTitle());
        System.out.println(
                " - Video id: " + returnedPlaylistItem.getSnippet().getResourceId().getVideoId());
        System.out.println(" - Posted: " + returnedPlaylistItem.getSnippet().getPublishedAt());
        System.out.println(" - Channel: " + returnedPlaylistItem.getSnippet().getChannelId());
        
        return returnedPlaylistItem.getId();
    }
}
