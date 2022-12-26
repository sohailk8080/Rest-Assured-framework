package com.rest.spotify.oauth2.api.application;

import com.rest.spotify.oauth2.utils.ConfigLoader;
import com.rest.spotify.oauth2.api.RestResource;
import com.rest.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.rest.spotify.oauth2.api.Route.*;
import static com.rest.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {
    public static Response createPlaylist(Playlist requestPlaylist) {
        return RestResource.createPlaylist(requestPlaylist, USERS + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken());

    }
    public static Response getCreatedPlaylist(String playlistID){
        return RestResource.getCreatedPlaylist(PLAYLISTS +"/", playlistID, getToken());

    }
    public static Response updateCreatedPlaylist(String playlistID, Playlist requestPlaylist){
        return RestResource.updateCreatedPlaylist(PLAYLISTS +"/", playlistID, requestPlaylist, getToken());
    }
    public static Response createPlaylistWithoutName(Playlist requestPlaylist){
        return RestResource.createPlaylistWithoutName(requestPlaylist, USERS + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken());
    }
    public static Response createPlaylistWithExpiredToken(String expired_token, Playlist requestPlaylist){
        return RestResource.createPlaylistWithExpiredToken("https://api.spotify.com",
                BASE_PATH, expired_token, requestPlaylist, USERS + ConfigLoader.getInstance().getUserId() + PLAYLISTS);
    }
}
