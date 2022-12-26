package com.rest.spotify.oauth2.api;

import com.rest.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;
import java.time.Instant;
import java.util.HashMap;

import static com.rest.spotify.oauth2.api.Route.API;
import static com.rest.spotify.oauth2.api.Route.TOKEN;

public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public synchronized static String getToken() {
        try{
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing Token....");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryInSecs = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryInSecs - 300);
            }
            else {
                System.out.println("Token is good to use");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return access_token;
    }

    private static Response renewToken() {
        HashMap<String, String> formParms = new HashMap<>();
        formParms.put("client_id", ConfigLoader.getInstance().getClientId());
        formParms.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParms.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParms.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

        Response response = RestResource.tokenMethod(formParms, API+TOKEN);
        if (response.statusCode() != 200) {
            throw new RuntimeException("Access token renewal failed");
        }
        return response;
    }
}
