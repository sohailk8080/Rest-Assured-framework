package com.rest.spotify.oauth2.api;

public enum StatusCodeAndMessage {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_400(400, "Missing required field: name"),
    CODE_401(401, "Invalid access token");

    public int code;
    public String msg;

    StatusCodeAndMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
