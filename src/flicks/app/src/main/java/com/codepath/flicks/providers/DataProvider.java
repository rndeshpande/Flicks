package com.codepath.flicks.providers;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * Created by rdeshpan on 9/10/2017.
 */

public class DataProvider {

    private final String apiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final OkHttpClient client = new OkHttpClient();


    public ArrayList<String> getAllMovies() {
        ArrayList<String> movies = new ArrayList<>();
        return movies;
    }
}
