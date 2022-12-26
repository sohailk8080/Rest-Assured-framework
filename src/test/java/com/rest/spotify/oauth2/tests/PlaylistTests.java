package com.rest.spotify.oauth2.tests;

import com.rest.spotify.oauth2.api.StatusCodeAndMessage;
import com.rest.spotify.oauth2.api.application.PlaylistApi;
import com.rest.spotify.oauth2.pojo.Error;
import com.rest.spotify.oauth2.pojo.Playlist;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.rest.spotify.oauth2.utils.FakerUtil.getPlaylistDesc;
import static com.rest.spotify.oauth2.utils.FakerUtil.getPlaylistName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("SPOTIFY")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest{
    //String access_token ="BQDyQ7-1CPbP-Cms3xrOpS660pF-jJkjX2U7p0f43j6a5oo8Sn5QJ2I1FYpTz9jaSk7mR0KUVP01r_kbGmOWR5JVBcpJqVFI_9ePRNjCdyVmVJ4Pfx4W06CjyquuItnQNY_eH2Xxb82qk08M7hyqmphKfHaA0zMRdgvzWa_3i7UICv86SwUxUQPB7FFU61BG2iEdov9yKEdBJRer83Br7aNF0tQZvZwV4FvqckOZHfYKheTBwIXIz3Sp4vvpgPKhcF9lKU17NwX_T1R6";
    private static String playlistID;

    @Step("This step demonstrates playlist builder")
    public Playlist playlistBuilder(String name, String desc, Boolean _public){
        return Playlist.builder().
                name(name).
                description(desc).
                _public(_public).
                build();
    }
    public void assertPlaylist(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }
    public void assertStatusCode(int actualStatuscode, int expectedStatuscode){
        assertThat(actualStatuscode, equalTo(expectedStatuscode));
    }

    @Story("CREATE A PLAYLIST")
    @Description("This describes Test Case 1")
    @Test(description = "Test Case 1::Create a Spotify Playlist")
    public void createPlaylist(){
        Playlist requestPlaylist = playlistBuilder(getPlaylistName(), getPlaylistDesc(), false);
        Response response = PlaylistApi.createPlaylist(requestPlaylist);
        assertPlaylist(response.as(Playlist.class), requestPlaylist);
        playlistID = response.path("id");
        assertStatusCode(response.statusCode(), StatusCodeAndMessage.CODE_201.code);

    }

    @Description("This describes Test Case 2")
    @Test(description = "Test Case 2::Get the created Spotify Playlist")
    public void getCreatedPlaylist(){
        PlaylistTests pt = new PlaylistTests();
        pt.createPlaylist();
        Playlist requestPlaylist = playlistBuilder("Spotify Updated Playlist", "Spotify playlist updated Using Rest Assured", false);
        Response response = PlaylistApi.getCreatedPlaylist(playlistID);
        System.out.println("Got playlistID for CreatedPlaylist::"+playlistID);
        assertStatusCode(response.statusCode(), StatusCodeAndMessage.CODE_200.code);
    }

    @Story("UPDATE A PLAYLIST")
    @Description("This describes Test Case 3")
    @Test(description = "Test Case 3::Update the created Spotify Playlist")
    public void updateCreatedPlaylist(){
        PlaylistTests pt = new PlaylistTests();
        pt.createPlaylist();
        Playlist requestPlaylist = playlistBuilder(getPlaylistName(), getPlaylistDesc(), false);
        Response response = PlaylistApi.updateCreatedPlaylist(playlistID, requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCodeAndMessage.CODE_200.code);
    }

    //Negative Test Case
    @Story("NO NAME PLAYLIST")
    @Description("This describes Negative Test Case 1")
    @Test(description = "Test Case 4::Create a Spotify Playlist without name")
    @Link("https://negative-test-case.org")
    @Link(name = "allure-negative-testing", type = "my-negative-testcase-link")
    @Issue("19008")
    @TmsLink("negative-test-1")
    public void createPlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("", getPlaylistDesc(), false);
        Response response = PlaylistApi.createPlaylistWithoutName(requestPlaylist);
        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(StatusCodeAndMessage.CODE_400.code));
        assertThat(error.getError().getMessage(), equalTo(StatusCodeAndMessage.CODE_400.msg));
    }

    //Negative Test Case
    @Description("This describes Negative Test Case 2")
    @Test(description = "Test Case 5::Create a Spotify Playlist with Expired Token")
    public void createPlaylistWithExpiredToken(){
        String expired_token = "12345";
        Playlist requestPlaylist = playlistBuilder(getPlaylistName(), getPlaylistDesc(), false);
        Response response = PlaylistApi.createPlaylistWithExpiredToken(expired_token, requestPlaylist);
        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(StatusCodeAndMessage.CODE_401.code));
        assertThat(error.getError().getMessage(), equalTo(StatusCodeAndMessage.CODE_401.msg));
    }
}

//Check the SpecBuilder class for environmental changes made
    //Use this to run the code using cmd
        //mvn test -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com"