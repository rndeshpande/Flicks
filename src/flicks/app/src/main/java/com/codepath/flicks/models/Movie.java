package com.codepath.flicks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rdeshpan on 9/10/2017.
 */

public class Movie {
    private final String baseUrl = "http://image.tmdb.org/t/p/";
    private final String posterSize = "w342/";
    private final String backdropSize = "w780/";

    @SerializedName("id")
    public  Integer id;

    @SerializedName("title")
    public  String title;

    @SerializedName("popularity")
    public  Double popularity;

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

    public String getPosterPath() {
        return baseUrl + posterSize  + posterPath;
    }

    public String getBackdropPath() {
        return  baseUrl + backdropSize + backdropPath;
    }
}
