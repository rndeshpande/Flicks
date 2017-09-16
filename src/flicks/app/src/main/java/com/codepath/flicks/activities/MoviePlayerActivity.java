package com.codepath.flicks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.codepath.flicks.models.MovieVideo;
import com.codepath.flicks.models.MovieVideosResponse;
import com.codepath.flicks.models.MoviesDbResponse;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MoviePlayerActivity extends YouTubeBaseActivity {

    @BindView(R.id.ytPlayer) YouTubePlayerView playerView;

    private final String API_KEY_YOUTUBE = "AIzaSyChFS5XMhK8kXSE-cecMW9ux1RKuADEBk4";
    private final String API_KEY_MOVIEDB =  "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final String baseUrl = "https://api.themoviedb.org/3/movie";

    private final OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_player);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final int movieId = intent.getIntExtra("movieId",-1);
        playMovie(movieId);
    }

    private void playMovie(int movieId) {
        Request request = new Request.Builder()
                .url(getApiUrlMovieDb(Integer.toString(movieId)))
                .build();

        Log.d("PLAYER", getApiUrlMovieDb(Integer.toString(movieId)));
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final MovieVideosResponse dbResponse = gsonBuilder.create().fromJson(responseBody.string(), MovieVideosResponse.class);
                    final ArrayList<MovieVideo> videos = dbResponse.videos;

                    if(videos.size() > 0)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadMovie(videos.get(0).key);
                            }
                        });

                }
            }
        });
    }

    private void loadMovie(final String videoId) {
        playerView.initialize(API_KEY_YOUTUBE,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(videoId);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    private String getApiUrlMovieDb(String movieId) {
        return baseUrl + "/" + movieId + "/videos?api_key=" + API_KEY_MOVIEDB;
    }
}
