package com.rest.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtil {
    public static String getPlaylistName(){
        Faker faker = new Faker();
        return "Playlist"+faker.regexify("[ A-Za-z0-9,_-]{10}");
    }
    public static String getPlaylistDesc(){
        Faker faker = new Faker();
        return "Playlist"+faker.regexify("[ A-Za-z0-9,_-!@#$%^&*()]{20}");
    }
}
