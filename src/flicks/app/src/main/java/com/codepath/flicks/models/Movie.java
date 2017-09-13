package com.codepath.flicks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rdeshpan on 9/10/2017.
 */

public class Movie {
    private final String baseUrl = "http://image.tmdb.org/t/p/";

    @SerializedName("id")
    public  Integer id;

    @SerializedName("title")
    public  String title;

    @SerializedName("vote_average")
    public  Double voteAverage;

    @SerializedName("poster_path")
    public  String posterPath;

    @SerializedName("backdrop_path")
    public  String backdropPath;

    @SerializedName("overview")
    public  String overview;

    @SerializedName("video")
    public  Boolean video;

    @SerializedName("release_date")
    public  String releaseDate;

    public String getPosterPath(String size) {
        return baseUrl + size + "/"  + posterPath;
    }

    public String getBackdropPath(String size) {
        return  baseUrl + size + "/" + backdropPath;
    }
}
