package com.rest.spotify.oauth2.api;


import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.rest.spotify.oauth2.api.Route.BASE_PATH;

public class SpecBuilder {
    //static String access_token ="BQB84Nl0unas9r4Zyv2utRb0AMe6cnaVnyiRRSJG_CW_pm1mDhJnTZqmKzGOTpRIcZ0UFsAFf7c3CmhaTiIStyifm66ps2sTJ0HDvArENlf4QH-X_zj9DkCr2a7CA7xrBggKMEm3eYYoBZDRaSax94t-mE5uRTpZng-kiXYgOJYsL1N3Q-BSOqBoZDtXUEjwQImNmV9lh7RWs0XvccNPsWBB5DynId1NshkxkMaWrXwDd9vE38bPacY0lmXX6quzO_jC9rvJ38vnMfkG";

    public static RequestSpecification getRequestSpecification(){
        return new RequestSpecBuilder().
                setBaseUri(System.getProperty("BASE_URI")).
                //setBaseUri("https://api.spotify.com").
                setBasePath(BASE_PATH).
                setContentType(ContentType.JSON).
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL).build();

    }
    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).build();
    }

    public static RequestSpecification getAccountRequestSpec(){
        return new RequestSpecBuilder().
                setBaseUri(System.getProperty("ACCOUNT_BASE_URI")).
                //setBaseUri("https://accounts.spotify.com").
                setContentType(ContentType.URLENC).
                log(LogDetail.ALL).
                build();


    }
}
