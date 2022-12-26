package com.rest.spotify.oauth2.api;

import com.rest.spotify.oauth2.pojo.Playlist;
import io.qameta.allure.AllureResultsWriter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.rest.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response createPlaylist(Playlist requestPlaylist, String path, String token) {
        return given(getRequestSpecification()).
                body(requestPlaylist).
                auth().oauth2(token).
        when()
                .post(path).
        then().spec(getResponseSpecification()).
                extract().response();
    }

    public static Response getCreatedPlaylist(String path, String playlistID, String token) {
        return given(getRequestSpecification()).
                auth().oauth2(token).
       when().
                get(path + playlistID).
       then().spec(getResponseSpecification()).
                assertThat().
                statusCode(200).extract().response();
    }

    public static Response updateCreatedPlaylist(String path, String playlistID, Playlist requestPlaylist, String token) {
        return given(getRequestSpecification()).
                body(requestPlaylist).
                auth().oauth2(token).
        when().
                put(path + playlistID).
        then().spec(getResponseSpecification()).
                extract().response();
    }

    public static Response createPlaylistWithoutName(Playlist requestPlaylist, String path, String token) {
        return given(getRequestSpecification()).
                body(requestPlaylist).
                auth().oauth2(token).
        when().
                post(path).
        then().spec(getResponseSpecification()).
                extract().response();
    }

    public static Response createPlaylistWithExpiredToken(String Uri, String bPath, String expired_token, Playlist requestPlaylist, String path) {
        return given().
                baseUri(Uri).
                basePath(bPath).
                body(requestPlaylist).
                auth().oauth2(expired_token).
                contentType(ContentType.JSON).
        when().
                post(path).
        then().spec(getResponseSpecification()).
                extract().response();
    }
    public static Response tokenMethod(HashMap<String, String> formParams, String path){
        return given(getAccountRequestSpec()).
                formParams(formParams).
        when().
                post(path).
        then().spec(getResponseSpecification()).
                extract().response();
    }
}
