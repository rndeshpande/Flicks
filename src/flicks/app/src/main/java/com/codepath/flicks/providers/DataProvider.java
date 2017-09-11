package com.codepath.flicks.providers;

import android.content.Context;

import com.codepath.flicks.MainActivity;
import com.codepath.flicks.adapters.MoviesAdapter;
import com.codepath.flicks.models.MoviesDbResponse;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
